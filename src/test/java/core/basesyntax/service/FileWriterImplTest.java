package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.FileReader;
import core.basesyntax.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String CSV_OUTPUT
            = "src/test/java/core/basesyntax/resources/finalReportTest.csv";
    private static final String CSV_OUTPUT_EXPECTED
            = "src/main/java/core/basesyntax/resources/finalReport.csv";
    private static FileWriter fileWriter;
    private static FileReader fileReader;

    @BeforeAll
    static void beforeAll() {
        fileWriter = new FileWriterImpl();
        fileReader = new FileReaderImpl();
    }

    @Test
    void write_ValidData_FileCreatedAndContainsCorrectData() throws IOException {
        String testData = "This is a test string";
        fileWriter.write(testData, CSV_OUTPUT);
        String fileContent = Files.readString(Path.of(CSV_OUTPUT));

        assertTrue(Files.exists(Path.of(CSV_OUTPUT)));
        assertEquals(testData, fileContent);
    }
}
