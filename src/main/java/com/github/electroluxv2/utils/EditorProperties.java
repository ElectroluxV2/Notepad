package com.github.electroluxv2.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class EditorProperties {
    private static final String ROOT_DIRECTORY = ".JavaFXPad";
    private static final String CONFIG_NAME = "app.config";

    // I know this class read file within every call but it make it reflecting to changes to file immediately without need to watch file changes
    public static void save(final String key, final String value) throws IOException {
        final var properties = getAppProperties();
        properties.setProperty(key, value);
        save(properties);
    }

    private static void save(final Properties properties) throws IOException {
        final var out = new FileOutputStream(getPropertiesFile());
        properties.store(out, "---JavaFXPad---");
        out.close();
    }

    public static void save() throws IOException {
        save(getDefaultProperties());
    }

    public static String getString(final String key) {
        try {
            return getAppProperties().getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean getBoolean(final String key) {
        try {
            return Boolean.parseBoolean(getAppProperties().getProperty(key));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static double getDouble(final String key) {
        try {
            return Double.parseDouble(getAppProperties().getProperty(key));
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static Properties getAppProperties() throws IOException {
        final var properties = new Properties(getDefaultProperties());
        final var inputStream = new FileInputStream(getPropertiesFile());
        properties.load(inputStream);
        inputStream.close();
        return properties;
    }

    private static Properties getDefaultProperties() throws IOException {
        final var classLoader = ClassLoader.getSystemClassLoader();
        final var defaultProperties = new Properties();
        var inputStream = classLoader.getResourceAsStream("app.config");
        defaultProperties.load(inputStream);
        Objects.requireNonNull(inputStream).close();
        return defaultProperties;
    }

    private static File getPropertiesFile() throws IOException {
        final File directory = new File(System.getProperty("user.home") + File.separator + ROOT_DIRECTORY);
        final File config = new File(System.getProperty("user.home") + File.separator + ROOT_DIRECTORY + File.separator + CONFIG_NAME);

        if (!directory.exists()) {
            if (!directory.mkdir()) {
                System.err.printf("Failed to create directory: %s%n", directory.getAbsolutePath());
            }
        }

        if (config.createNewFile()) {
            System.err.printf("Created new config file: %s", config.getAbsolutePath());
        }

        return config;
    }
}
