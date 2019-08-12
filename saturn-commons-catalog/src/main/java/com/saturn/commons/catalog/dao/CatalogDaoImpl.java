package com.saturn.commons.catalog.dao;

import com.saturn.commons.catalog.CatalogConfig;
import com.saturn.commons.catalog.CatalogItemHandler;
import com.saturn.commons.database.IntegerHandler;
import com.saturn.commons.database.NullIntegerHandler;
import com.saturn.commons.utils.MapUtils;
import com.saturn.commons.utils.TimeUtils;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Catalog DAO Implementation
 */
public class CatalogDaoImpl implements CatalogDao {

    /** Logger */
    protected Logger LOG;

    /** CatalogProvider configuration */
    protected CatalogConfig conf;

    /** DataSource */
    protected DataSource dataSource;

    /** SQL map */
    protected Map<CatalogQuery,String> sqlMap;


    /**
     * Constructor
     * @param config
     * @param dataSource
     */
    public CatalogDaoImpl(CatalogConfig config,DataSource dataSource) {
        this.conf = config;
        this.dataSource = dataSource;
        this.LOG= LogManager.getLogger( conf.getCatalogId()!=null? conf.getCatalogId() : this.getClass().getName() );
        initSql(config);
    }



    /**
     * Init SQL statements
     * @param conf
     */
    private void initSql(CatalogConfig conf) {
        sqlMap= new EnumMap(CatalogQuery.class);

        // Select ID for given value
        String sql = "SELECT " + conf.getIdColumnName() +
                " FROM " + conf.getTableName() +
                " WHERE " + conf.getValueColumnName() + "=?";
        sqlMap.put(CatalogQuery.SELECT_VALUE, sql);

        // Retrieve all items
        sql= "SELECT " + conf.getIdColumnName()+","+conf.getValueColumnName()+
                " FROM " + conf.getTableName()+
                " LIMIT "+conf.getIniSize();
        sqlMap.put(CatalogQuery.ALL_ITEMS, sql);

        if (conf.isCreateIfNotFound()) {
            sql="INSERT INTO "+conf.getTableName() +
                "("+conf.getValueColumnName()+") VALUES(?)";
            sqlMap.put(CatalogQuery.INSERT_VALUE, sql);
        }

        if (conf.isStatsEnabled()) {
            // Update hit counter
            sql= "UPDATE "+conf.getTableName() +
                " SET "+conf.getCountColumnName() + "="+conf.getCountColumnName()+"+?" +
                " ,"+conf.getChangeDateColumnName()+"=?" +
                " WHERE "+conf.getIdColumnName() + "=?";
            sqlMap.put(CatalogQuery.UPDATE_COUNTER, sql);

            // Retrieve most used items
            sql="SELECT "+conf.getIdColumnName()+","+conf.getValueColumnName()+
                    " FROM "+conf.getTableName()+
                    " WHERE "+conf.getChangeDateColumnName()+" BETWEEN DATE_SUB(CURRENT_DATE(),INTERVAL "+conf.getIniHistoryDays()+" DAY) AND CURRENT_TIMESTAMP()" +
                    " ORDER BY "+conf.getCountColumnName()+" DESC"+
                    " LIMIT "+conf.getIniSize();
            sqlMap.put(CatalogQuery.MOST_USED_ITEMS, sql);
        }

        if (LOG.isDebugEnabled())
            LOG.debug(MapUtils.map2PlainString(sqlMap, "Catalog Queries","\n\t"));

    }



    @Override
    public Integer getValueId(String value) throws Exception {
        QueryRunner qr = new QueryRunner(dataSource);
        return qr.query(sqlMap.get(CatalogQuery.SELECT_VALUE), new NullIntegerHandler(), value);
    }


    @Override
    public Integer getOrCreateValueId(String value) throws Exception {
        // Try retrieving first...
        QueryRunner qr = new QueryRunner(dataSource);
        Integer id= qr.query(sqlMap.get(CatalogQuery.SELECT_VALUE), new NullIntegerHandler(), value);

        // Not found? -> create!
        if (id==null) {
            id= qr.insert(sqlMap.get(CatalogQuery.INSERT_VALUE),new IntegerHandler(),value);
        }

        return id;
    }


    @Override
    public void loadItems(CatalogQuery query, CatalogItemHandler handler) {
        long t= System.currentTimeMillis();
        LOG.debug("Loading items");
        try {
            QueryRunner qr= new QueryRunner(dataSource);
            int n= qr.query(sqlMap.get(query), handler);
            t= System.currentTimeMillis()-t;
            LOG.debug("[{}] Items loaded in {}",n,TimeUtils.milisToString(t));

        } catch (Exception e) {
            LOG.error("Error fetching items", e);
        }
    }


    @Override
    public int[] updateHitCount(List<Object[]> params) throws Exception {
        QueryRunner qr= new QueryRunner(dataSource);
        Object[][] arrPars= new Object[params.size()][];
        params.toArray(arrPars);
        int[] res= qr.batch(sqlMap.get(CatalogQuery.UPDATE_COUNTER), arrPars);
        return res;
    }


    @Override
    public void release() {
        if (sqlMap!=null) {
            sqlMap.clear();
            sqlMap=null;
        }
        conf=null;
        dataSource=null;
        LOG=null;
    }

}
