package com.saturn.commons.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;



/**
 * Fabrica de Threads Nombrados.
 * @author Raul del Cid Lopez
 */
public class NamedThreadFactory implements ThreadFactory
{
    /** Nombre base para los Threads */
    private String name;

    /** Los Threads son demonios? */
    private boolean daemon;

    /** Contador */
    private AtomicInteger counter;

    /** Fabricas de Threads con Nombre */
    private static Map<String,NamedThreadFactory> factories=new HashMap<String,NamedThreadFactory>();



    /**
     * Devuelve una Fabrica de Threads, cuyos threads tendran de nombre el
     * prefijo dado. Los Threads creados no corren como demonio.
     * @param name Nombre base de los Threads
     * @return
     */
    public static NamedThreadFactory getFactory(String name)
    {
        return getFactory(name,false);
    }



    /**
     * Devuelve una fabrica de Threads nombrados
     * @param name Prefijo de los threads
     * @param daemon Los threads a crear seran demonios?
     * @return
     */
    public static synchronized NamedThreadFactory getFactory(String name,boolean daemon)
    {
        String key=name+(daemon?"1":"0");
        NamedThreadFactory factory= factories.get(key);

        if (factory==null)
        {
            factory= new NamedThreadFactory(name, daemon);
            factories.put(key, factory);
        }

        return factory;
    }



    /**
     * Crea una Fabrica de Threads cuyos Threads son nombrados con el nombre dado
     * de 1 hasta N...
     * @param name Prefijo que tendran los nombres de los Threads
     */
    private NamedThreadFactory(String name,boolean daemon)
    {
        this.name=name;
        counter= new AtomicInteger();
    }



    /**
     * Invocado para Crear un Nuevo Thread
     * @param r
     * @return
     */
    public Thread newThread(Runnable r)
    {
        Thread t=new Thread(r, getName());
        t.setDaemon(daemon);
        return t;
    }



    /**
     * Crea un Nombre para el siguiente Thread
     * @return Nuevo Nombre del Thread
     */
    private final String getName()
    {
        StringBuilder threadName= new StringBuilder(name);
        threadName.append(' ').append(counter.incrementAndGet() );
        return threadName.toString();
    }

}
