package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterServiceTest {
    private static final String TEST_RESOURCES_PATH = "src/test/resources/";
    private static final String TEST_NON_EXISTENT_PATH = "src/test/non-existent/";
    private static WriterService writer;

    @BeforeAll
    static void beforeAll() {
        writer = new WriterServiceImpl();
    }

    @BeforeEach
    void beforeEach() {
        Path path = Paths.get(TEST_RESOURCES_PATH + "test_report_file.csv");
        try {
            Files.deleteIfExists(path);
            Files.createFile(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void write_reportToFile_ok() {
        String reportString = "fruit,quantity\nbanana,50\norange,10\napple,20";
        writer.writeReportToFile(reportString, TEST_RESOURCES_PATH + "test_report_file.csv");
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new FileReader(TEST_RESOURCES_PATH + "test_report_file.csv"))) {
            String stringLine = reader.readLine();
            while (stringLine != null) {
                builder.append(stringLine).append("\n");
                stringLine = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't find file by path: "
                    + TEST_RESOURCES_PATH + "test_report_file.csv", e);
        }
        String resultString = builder.toString().trim();
        assertEquals(reportString, resultString);
    }

    @Test
    void write_toWrongPath_notOk() {
        String reportString = "fruit,quantity\nbanana,50\norange,10\napple,20";
        assertThrows(RuntimeException.class, () -> writer
                .writeReportToFile(reportString, TEST_NON_EXISTENT_PATH + "test_report_file.csv"));
    }
}
