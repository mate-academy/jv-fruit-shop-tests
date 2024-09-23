package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String CSV_OUTPUT_TO_TEST
            = "src/test/resources/finalReportTest.csv";
    private static FileWriter fileWriter;

    @BeforeAll
    static void beforeAll() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void write_ValidData_FileCreatedAndContainsCorrectData() throws IOException {
        String testDataExpected = "This is a test string";
        fileWriter.write(testDataExpected, CSV_OUTPUT_TO_TEST);
        String fileContent = Files.readString(Path.of(CSV_OUTPUT_TO_TEST));

        assertTrue(Files.exists(Path.of(CSV_OUTPUT_TO_TEST)));
        assertEquals(testDataExpected, fileContent);
    }
}
