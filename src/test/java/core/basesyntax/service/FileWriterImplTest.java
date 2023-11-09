package core.basesyntax.service;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String FILE_NAME = "test_report.csv";
    private FileWriter fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void testWriteReport_Successful_Ok() {
        // Arrange
        Map<String, Integer> reportMap = new HashMap<>();
        reportMap.put("apple", 20);
        reportMap.put("banana", 15);

        // Act
        fileWriter.writeReport(reportMap, FILE_NAME);

        // Assert
        assertTrue(Files.exists(Paths.get(FILE_NAME)));

        //Clean
        cleanUp();
    }

    private void cleanUp() {
        try {
            Files.deleteIfExists(Paths.get(FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException("Delete file after test");
        }
    }
}
