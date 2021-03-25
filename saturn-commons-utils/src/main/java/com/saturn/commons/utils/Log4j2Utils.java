package com.saturn.commons.utils;



import java.io.File;
import java.io.IOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.LoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;



/**
 * Clase usada para configurar el sistema de Loggeo (Log4j) de mensajes de salida
 * de los diferentes procesos.
 * @author Raul del Cid Lopez
 */
public class Log4j2Utils
{
    /** Formato de Log de Salida */
    public static final String LOG_FMT="%d{yyyy.MM.dd HH:mm:ss.SSS} [%-10.10t] %-5p %c{1}.%M - %m%n";

    /** Configuracion realizada? */
    private static boolean configured=false;



    /**
     * Constructor Privado
     */
    private Log4j2Utils() {
    }



    /**
     * Inicializa el Logger
     */
    public final static synchronized void initLog4j2() {

        // Configuracion ya realizada?
        if (configured) return;

        // Archivo default para cargar configuracion
        File logPropFile= new File("log4j2.xml");

        // Si archivo existe cargamos configuracion
        if (logPropFile.exists()) {
            System.out.println("initLog4J: FILE["+logPropFile.getAbsolutePath()+"] found!, loading configuration...");
            LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);

            // this will force a reconfiguration
            context.setConfigLocation(logPropFile.toURI());
            configured=true;
        }
        else // Si no existe creamos configuracion default
            buildDefaultConfig();
    }



    /**
     * Crea configuracion default para Log4J
     * @param logPropFile
     */
    private static final void buildDefaultConfig() {

        System.out.println("log4j2.xml not found, using default configuration!");
        ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();

        // Pattern layout
        LayoutComponentBuilder layout = builder.newLayout("PatternLayout");
        layout.addAttribute("pattern", LOG_FMT);

        // Console appender...
        AppenderComponentBuilder console = builder.newAppender("out", "Console");
        console.add(layout);
        builder.add(console);

        // Add Root Logger
        RootLoggerComponentBuilder rootLogger= builder.newRootLogger(Level.TRACE);
        rootLogger.add(builder.newAppenderRef("out"));
        builder.add(rootLogger);

        // Add additional loggers
        LoggerComponentBuilder logger = builder.newLogger("com", Level.TRACE);
        logger.add(builder.newAppenderRef("out"));
        logger.addAttribute("additivity", false);
        builder.add(logger);

        logger = builder.newLogger("biz", Level.TRACE);
        logger.add(builder.newAppenderRef("out"));
        logger.addAttribute("additivity", false);
        builder.add(logger);

        try {
            builder.writeXmlConfiguration(System.out);
        } catch (IOException e) {
        }

        // Initialize the Log4j2 System...
        Configurator.initialize(builder.build());


        configured= true;
    }



}
