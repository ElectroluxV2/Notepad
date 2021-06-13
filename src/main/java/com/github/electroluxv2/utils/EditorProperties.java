package com.github.electroluxv2.utils;

import com.github.electroluxv2.components.ErrorAlert;

import java.io.*;
import java.util.Properties;

public class EditorProperties {
    private static final String ROOT_DIRECTORY = ".JavaFXPad";
    private static final String CONFIG_NAME = "app.config";

    // I know this class read file within every call but it make it reflecting to changes to file immediately without need to watch file changes
    public static void save(final String key, final String value) {
        final var properties = getAppProperties();
        properties.setProperty(key, value);
        save(properties);
    }

    private static void save(final Properties properties) {
        try (final FileOutputStream out = new FileOutputStream(getPropertiesFile())) {
            properties.store(out, "---JavaFXPad---");
        } catch (final IOException e) { ErrorAlert.Show(e); }

    }

    public static void save() {
        save(getDefaultProperties());
    }

    public static String getString(final String key) {
        return getAppProperties().getProperty(key);
    }

    public static boolean getBoolean(final String key) {
        return Boolean.parseBoolean(getAppProperties().getProperty(key));
    }

    public static double getDouble(final String key) {
        return Double.parseDouble(getAppProperties().getProperty(key));
    }

    private static Properties getAppProperties() {
        final var properties = new Properties(getDefaultProperties());
        final FileInputStream inputStream;
        try {
            inputStream = new FileInputStream(getPropertiesFile());
            properties.load(inputStream);
            inputStream.close();
        } catch (final IOException e) { ErrorAlert.Show(e); }

        return properties;
    }

    private static Properties getDefaultProperties() {
        final var classLoader = ClassLoader.getSystemClassLoader();
        final var defaultProperties = new Properties();
        try (var inputStream = classLoader.getResourceAsStream("app.config")) {
            defaultProperties.load(inputStream);
        } catch (final IOException e) { ErrorAlert.Show(e); }
        return defaultProperties;
    }

    private static File getPropertiesFile() {
        final File directory = new File(System.getProperty("user.home") + File.separator + ROOT_DIRECTORY);
        final File config = new File(System.getProperty("user.home") + File.separator + ROOT_DIRECTORY + File.separator + CONFIG_NAME);

        if (!directory.exists()) {
            if (!directory.mkdir()) {
                ErrorAlert.Show(new Exception("Failed to create directory: %s%n".formatted(directory.getAbsolutePath())));
            }
        }

        try {
            if (config.createNewFile()) {
                System.err.printf("Created new config file: %s", config.getAbsolutePath());
            }
        } catch (final IOException e) { ErrorAlert.Show(e); }

        return config;
    }
}
