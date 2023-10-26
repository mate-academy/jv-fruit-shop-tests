package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvWriterTest {
    private static final String FILE = "src/test/resources/report.csv";
    private static final String WRONG_FILE = "src/test/badResources/report.csv";
    private static final List<String> EXPECTED_LIST = List.of("fruit,quantity",
            "apple,90", "banana,132");
    private static final String DATA = """
            fruit,quantity
            apple,90
            banana,132""";
    private static CsvWriter writer;

    @BeforeAll
    static void beforeAll() {
        writer = new CsvWriter();
    }

    @Test
    void writeReportToFile_isOk() {
        writer.writeReportToFile(DATA, FILE);
        List<String> actualList;
        try {
            actualList = Files.readAllLines(Path.of(FILE));
        } catch (IOException e) {
            throw new RuntimeException("Cant read data from file: " + FILE);
        }

        assertEquals(EXPECTED_LIST, actualList);
    }

    @Test
    void writeReportToFile_badPath_notOk() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            writer.writeReportToFile(DATA, WRONG_FILE);
        },"If path is incorrect it should throw Exception!!!");

        String expectedMessage = "Can't write data to file: ";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
