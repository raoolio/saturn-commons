package com.saturn.commons.catalog;

import com.saturn.commons.catalog.dao.CatalogDao;
import com.saturn.commons.catalog.id.CachedId;
import com.saturn.commons.utils.time.TimeUtils;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ParameterizedMessage;


/**
 * Expired Item Handler
 */
public class ItemRemovedHandler extends Thread implements RemovalListener<String,CachedId>,Runnable {

    /** Logger */
    protected Logger LOG;

    /** CatalogProvider configuration */
    protected CatalogConfig conf;

    /** DataSource */
    protected CatalogDao dao;

    /** Cache de contadores */
    private Map<Integer,AtomicLong> countCache;

    /** Hora de ultima actualizacion */
    private long nextCountUpdate;

    /** Exit flag */
    private boolean done;

    /** Queue object */
    private BlockingQueue<RemovalNotification<String, CachedId>> queue;



    /**
     * Constructor
     * @param conf Catalog Config
     * @param dao CatalogDao instance
     */
    public ItemRemovedHandler(CatalogConfig conf, CatalogDao dao) {
        super("ExpiredHandler");
        this.conf = conf;
        this.dao = dao;
        this.queue= new LinkedBlockingQueue();
        this.countCache= new HashMap(conf.getMaxSize());
        setNextUpdateTime();
        LOG= LogManager.getLogger( conf.getCatalogId()!=null? conf.getCatalogId() : this.getClass().getName() );
    }



    /**
     * Calculates the next update time
     * @return
     */
    private void setNextUpdateTime() {
        nextCountUpdate= System.currentTimeMillis()+conf.getCountUpdatePeriod();
    }



    /**
     * LoadingCache item removal listener
     * @param notif Notification to enqueue for parallel processing
     */
    @Override
    public void onRemoval(RemovalNotification<String, CachedId> notif) {
        if (!queue.offer(notif))
            LOG.warn("{} Expired Handler Queue is FULL",conf.getCatalogId());
    }



    @Override
    public void run() {

        while (!done) {
            try {
                RemovalNotification<String, CachedId> rn= queue.poll(1, TimeUnit.SECONDS);

                // Something to do ?
                if (rn!=null) {
                    CachedId cid= rn.getValue();

                    if (cid.getCount()>0) {
                        LOG.trace("Entry removed: {} -> {}",rn.getKey(),cid);
                        AtomicLong count= getCounter(cid.getId());
                        count.addAndGet(cid.getCount());
                    }
                // Persist counters?
                } else if (System.currentTimeMillis() > nextCountUpdate) {
                    saveCounters();
                }
            } catch (InterruptedException ex) {
                done=true;
                LOG.warn("{} Removal Handler interrupted",conf.getCatalogId());
            }
        }
    }



    /**
     * Returns the corresponding counter for the given id
     * @param id Item ID
     * @return
     */
    private AtomicLong getCounter(Integer id) {
        AtomicLong counter= countCache.get(id);

        // Not found? -> Create it!
        if (counter==null) {
            counter= new AtomicLong();
            countCache.put(id, counter);
        }

        return counter;
    }



    /**
     * Persist current counters
     */
    private void saveCounters() {
        long t= System.currentTimeMillis();
        int size=0;

        try {
            size= countCache.size();

            if (size>0) {
                LOG.info("Analyzing {} counters",size);
                List<AtomicLong> counters= new LinkedList<AtomicLong>();
                List<Object[]> params= new LinkedList<Object[]>();
                Iterator<Integer> it= countCache.keySet().iterator();
                Timestamp changeDate= new Timestamp(t);

                // loop through counters
                while (it.hasNext()) {
                    Integer id= it.next();
                    AtomicLong counter= countCache.get(id);

                    // Something to save?
                    if (counter.get() > 0) {
                        counters.add( counter );
                        params.add( new Object[]{ counter.get(), changeDate, id } );
                    } else
                        it.remove();
                }

                // Run batch update?
                if (!params.isEmpty()) {
                    LOG.info("Updating {} counters",params.size());
                    int[] res= dao.updateHitCount(params);

                    // Substract persisted counters
                    Iterator<AtomicLong> sit= counters.iterator();
                    int p=0;
                    while (sit.hasNext()) {
                        int update=res[p++];
                        AtomicLong counter= sit.next();
                        if (update>0) {
                            counter.set(0);
                        }
                    }
                    t= System.currentTimeMillis()-t;
                    LOG.info("{} Counters updated in {}",params.size(),TimeUtils.milisToString(t));
                } else
                    LOG.info("Counters analyzed, nothing to update");

            } else
                LOG.info("No counters to update");
        } catch (Exception ex) {
            LOG.error(new ParameterizedMessage("Error updating {} counters",size), ex);
        } finally {
            setNextUpdateTime();
        }
    }



    /**
     * Release resources
     */
    public void release() {

        if (countCache!=null) {
            saveCounters();
            countCache.clear();
            countCache=null;
        }

        if (queue!=null) {
            queue.clear();
            queue=null;
        }
        dao=null;
        conf=null;
    }


}
