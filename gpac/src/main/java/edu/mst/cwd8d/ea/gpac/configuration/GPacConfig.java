package edu.mst.cwd8d.ea.gpac.configuration;

import org.apache.commons.configuration2.Configuration;

/**
 * @author Connor Walsh (cwd8d)
 *
 * Configuration class for GPac
 *
 * Each method simply returns the requested property and the overridden toString method allows
 * for easy output to a log file
 */
public class GPacConfig extends AbstractConfig {

    public GPacConfig() {}
    public GPacConfig(Configuration config) {
        super(config);
    }

    public int getWidth() {
        return getInt("width", 10);
    }

    public int getHeight() {
        return getInt("height", 10);
    }

    public int getFruitScore() {
        return getInt("fruitScore", 10);
    }

    public int getTimeMultiplier() {
        return getInt("timeMultiplier", 2);
    }

    public double getPillDensity() {
        return getDouble("pillDensity", 0.5);
    }

    public double getWallDensity() {
        return getDouble("wallDensity", 0.50);
    }

    public double getFruitSpawnProbability() {
        return getDouble("fruitSpawnProbability", 0.05);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("# GPac Configuration Properties: ");
        sb.append("\n# Width is ").append(getWidth());
        sb.append("\n# Height is ").append(getHeight());
        sb.append("\n# Fruit score is ").append(getFruitScore());
        sb.append("\n# Time multiplier is ").append(getTimeMultiplier());
        sb.append("\n# Pill density is ").append(getPillDensity());
        sb.append("\n# Wall density is ").append(getWallDensity());
        sb.append("\n# Fruit spawn probability is ").append(getFruitSpawnProbability());
        return sb.toString();
    }
}
