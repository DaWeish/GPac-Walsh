package edu.mst.cwd8d.ea.gpac.configuration;

import org.apache.commons.configuration2.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.NoSuchElementException;

/**
 * @author Connor Walsh (cwd8d)
 *
 * This base class provides a simple base for making derived configuration classes
 */
public class AbstractConfig {
    private Configuration config;
    private final Logger log = LoggerFactory.getLogger(AbstractConfig.class);

    public AbstractConfig() {}

    public AbstractConfig(Configuration config) {
        this.config = config;
    }

    /**
     * Gets an integer from the configuration file
     * @param name of the property to get
     * @param defaultValue which value to assign if the given name is not in the file or no file provided.
     * @return the integer value requested
     */
    protected int getInt(String name, int defaultValue) {
        if (config == null) return defaultValue;
        int value;
        try {
            value = config.getInt(name);
        } catch (NoSuchElementException e) {
            log.info("No value specified for " + name + ". Using default of " + defaultValue);
            value = defaultValue;
        }
        return value;
    }

    protected double getDouble(String name, double defaultValue) {
        if (config == null) return defaultValue;
        double value;
        try {
            value = config.getDouble(name);
        } catch (NoSuchElementException e) {
            log.info("No value specified for " + name + ". Using default of " + defaultValue);
            value = defaultValue;
        }
        return value;
    }

    protected boolean getBoolean(String name, boolean defaultValue) {
        if (config == null) return defaultValue;
        boolean value;
        try {
            value = config.getBoolean(name);
        } catch (NoSuchElementException e) {
            log.info("No value specified for " + name + ". Using default of " + defaultValue);
            value = defaultValue;
        }
        return value;
    }

    protected long getLong(String name, long defaultValue) {
        if (config == null) return defaultValue;
        long value;
        try {
            value = config.getLong(name);
        } catch (NoSuchElementException e) {
            log.info("No value specified for " + name + ". Using default of " + defaultValue);
            value = defaultValue;
        }
        return value;
    }

    public String getString(String name, String defaultValue) {
        if (config == null) return defaultValue;
        String value = config.getString(name);
        if (value == null) {
            log.info("No value specified for " + name + ". Using default of " + defaultValue);
            value = defaultValue;
        }
        return value;
    }
}
