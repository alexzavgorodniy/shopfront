package configJDBC;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Config {

    private static final String DEFAULT_PROPERTIES = "database.properties";

    private static Config instance;

    private final Properties props;

    private Config(String propertyFile) {
        String properties = propertyFile == null ? DEFAULT_PROPERTIES : propertyFile;
        try (InputStream stream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(properties)) {
            props = new Properties();
            props.load(stream);
        } catch (IOException e) {
            throw new IllegalStateException("Could not load database properties");
        }
    }

    public static Config getInstance() {
        return getInstance(null);
    }

    private static Config getInstance(String fileName) {
        if (instance == null) {
            instance = new Config(fileName);
        }
        return instance;
    }

    public String driver() {
        return props.getProperty(Props.DRIVER.getKey());
    }

    public String url() {
        return props.getProperty(Props.URL.getKey());
    }

    public String user() {
        return props.getProperty(Props.USER.getKey());
    }

    public String password() {
        return props.getProperty(Props.PASSWORD.getKey());
    }
}
