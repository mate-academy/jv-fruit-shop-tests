package core.basesyntax.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static final String OUTPUT_CSV_FILE_TEST = "src/test/resources/outputDataTest.csv";
    private WriterServiceImpl writer;

    @BeforeEach
    void setUp() {
        writer = new WriterServiceImpl();
    }

    @Test
    void writeToFile_wrongPath_notOk() {
        Assertions.assertThrows(
                RuntimeException.class,
                () -> writer.writeToFile("test/src/test.csv", "report")
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
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(OUTPUT_CSV_FILE_TEST));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from test file: " + OUTPUT_CSV_FILE_TEST, e);
        }
        Assertions.assertIterableEquals(expected, actual);
    }
}
