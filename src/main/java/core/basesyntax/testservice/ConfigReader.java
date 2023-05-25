package core.basesyntax.testservice;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigReader {
    private static final Map<String, String> propertyMap = new HashMap<>();
    private Properties properties;

    private void loadProperties() {
        try (FileInputStream fis = new FileInputStream("src/main/resources/config.properties")) {
            properties = new Properties();
            properties.load(fis);
            for (String key : properties.stringPropertyNames()) {
                String value = properties.getProperty(key);
                if (!isValueInMap(value)) {
                    propertyMap.put(key, value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isValueInMap(String value) {
        return propertyMap.containsValue(value);
    }

    public String getValueByKey(String userKey) {
        if (!propertyMap.containsKey(userKey)) {
            loadProperties();
        }
        return propertyMap.get(userKey);
    }

}





