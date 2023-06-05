package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static final String OUTPUT_CSV_FILE_TEST = "src/test/resources/outputDataTest.csv";
    private static final String WRONG_PATH = "test/src/test.csv";
    private static WriterService writer;

    @BeforeAll
    static void beforeAll() {
        writer = new WriterServiceImpl();
    }

    @Test
    void writeToFile_wrongPath_notOk() {
        assertThrows(
                RuntimeException.class,
                () -> writer.writeToFile(WRONG_PATH, "report")
        );
    }

    @Test
    void writeToFile_correctPath_ok() {
        String report = "fruit,quantity\n"
                + "banana,152\n"
                + "apple,90";
        writer.writeToFile(OUTPUT_CSV_FILE_TEST, report);
        List<String> expected = List.of(
                "fruit,quantity",
                "banana,152",
                "apple,90"
        );
        List<String> actual = readFromFile();

        assertIterableEquals(expected, actual);
    }

    private List<String> readFromFile() {
        try {
            return Files.readAllLines(Path.of(OUTPUT_CSV_FILE_TEST));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from test file: " + OUTPUT_CSV_FILE_TEST, e);
        }
    }
}
