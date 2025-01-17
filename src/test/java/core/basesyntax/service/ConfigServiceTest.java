package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConfigServiceTest {
    private static final String TEST_CONFIG_FILE = "test_config.properties";
    private static final String INVALID_CONFIG_FILE = "nonexistent_config.properties";
    private static final String MISSING_PROPERTY_FILE = "missing_property_config.properties";
    private static final String INVALID_FILE = "invalid_config.properties";

    @BeforeEach
    void setUp() throws IOException {
        try (FileWriter writer = new FileWriter(TEST_CONFIG_FILE)) {
            writer.write("source.path=/test/source\n");
            writer.write("target.path=/test/target\n");
        }
    }

    @AfterEach
    void tearDown() {
        new File(TEST_CONFIG_FILE).delete();
        new File(MISSING_PROPERTY_FILE).delete();
        new File(INVALID_FILE).delete();
    }

    @Test
    void constructor_validFile_loadsConfigSuccessfully() {
        ConfigService configService = new ConfigService(TEST_CONFIG_FILE);
        assertEquals("src/test/data/fruit_shop_transactions_test", configService.getSourcePath(),
                "Source path should match the property value");
        assertEquals("src/test/reports/report_test", configService.getTargetPath(),
                "Target path should match the property value");
    }

    @Test
    void constructor_fileNotFound_throwsRuntimeException() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> new ConfigService(INVALID_CONFIG_FILE),
                "Should throw RuntimeException when the file is not found");
        assertTrue(exception.getMessage().contains("Sorry, unable to find " + INVALID_CONFIG_FILE),
                "Error message should contain file name");
    }

    @Test
    void getSourcePath_propertyMissing_returnsNull() throws IOException {
        try (FileWriter writer = new FileWriter(MISSING_PROPERTY_FILE)) {
            writer.write("target.path=/test/target\n");
        }
        ConfigService configService = new ConfigService(MISSING_PROPERTY_FILE);
        assertNull(configService.getSourcePath(),
                "Source path should be null if property is not present");
    }

    @Test
    void getTargetPath_propertyMissing_returnsNull() throws IOException {
        try (FileWriter writer = new FileWriter(MISSING_PROPERTY_FILE)) {
            writer.write("source.path=/test/source\n");
        }
        ConfigService configService = new ConfigService(MISSING_PROPERTY_FILE);
        assertNull(configService.getTargetPath(),
                "Target path should be null if property is not present");
    }
}
