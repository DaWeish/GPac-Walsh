package edu.mst.cwd8d.ea.gpac.configuration;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Connor Walsh (cwd8d)
 *
 * Provides the configuration object given a config file
 */
public class GPacConfigProvider {
    private static Logger log = LoggerFactory.getLogger(GPacConfigProvider.class);

    /**
     * Get the default GPacConfig object
     * @return the default GPacConfig
     */
    public static GPacConfig getConfiguration() {
        return new GPacConfig();
    }

    /**
     * Get the GPacConfig based on a filename
     * @param filename which file to use for configuration
     * @return the GPacConfig object based on filename
     */
    public static GPacConfig getConfiguration(String filename) {
        Configurations configs = new Configurations();
        try {
            Configuration config = configs.properties(filename);
            return new GPacConfig(config);

        } catch (ConfigurationException ex) {
            log.error("Failed to configure GPac from provided Properties file: " + filename, ex);
            return null;
        }
    }
}
