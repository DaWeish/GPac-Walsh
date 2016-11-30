package edu.mst.cwd8d.ea.gpac.configuration;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Connor Walsh (cwd8d)
 *
 * This class provides configurations for the Experiment
 */
public class ExperimentConfigProvider {
    private static Logger log = LoggerFactory.getLogger(ExperimentConfigProvider.class);

    /**
     * Get a purely default ExperimentConfig
     * @return the default ExperimentConfig
     */
    public static ExperimentConfig getConfiguration() {
        return new ExperimentConfig();
    }

    /**
     * Get an ExperimentConfig based on the passed in filename
     * @param filename which file to use for configuration
     * @return the configuration object
     */
    public static ExperimentConfig getConfiguration(String filename) {
        Configurations configs = new Configurations();
        try {
            Configuration config = configs.properties(filename);
            return new ExperimentConfig(config);

        } catch (ConfigurationException ex) {
            log.error("Failed to configure GPac from provided Properties file: " + filename, ex);
            return null;
        }
    }
}
