package core.basesyntax;

import core.basesyntax.impl.CsvFileWriter;
import core.basesyntax.service.FileWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvFileWriterTest {
    private static final String TEST_FILE_PATH = "src/test/resources/test_output.csv";
    private FileWriter fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new CsvFileWriter();
    }

    @AfterEach
    void tearDown() {
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void writeReport_validData_ok() throws IOException {
        String testData = "header1,header2\nvalue1,value2";
        fileWriter.writeReport(testData, TEST_FILE_PATH);
        File file = new File(TEST_FILE_PATH);
        Assertions.assertTrue(file.exists());
        Assertions.assertEquals(testData,
                new String(Files.readAllBytes(Paths.get(TEST_FILE_PATH))));
    }

    @Test
    void writeReport_invalidPath_notOk() {
        String testData = "Some data";
        String invalidPath = "/invalid_folder/test.csv";
        Exception exception = Assertions.assertThrows(RuntimeException.class,
                () -> fileWriter.writeReport(testData, invalidPath));
        Assertions.assertTrue(exception.getMessage().contains("Failed to write report to file"));
    }
}
