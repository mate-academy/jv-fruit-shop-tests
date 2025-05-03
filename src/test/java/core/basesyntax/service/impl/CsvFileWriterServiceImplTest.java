package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvFileWriterServiceImplTest {
    private static final String VALID_FILE_PATH = "src/test/resources/validOutput.csv";
    private static final String WRONG_PATH = "src/test/resources/";
    private static final String REPORT = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator()
            + "apple,90" + System.lineSeparator();
    private static FileWriterService fileWriterService;

    @BeforeAll
    static void beforeAll() {
        fileWriterService = new CsvFileWriterServiceImpl();
    }

    @Test
    void writeToFile_validData_ok() {
        fileWriterService.writeToFile(REPORT, VALID_FILE_PATH);
        List<String> expected = List.of("fruit,quantity", "banana,152", "apple,90");
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(VALID_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file" + VALID_FILE_PATH, e);
        }
        assertEquals(expected, actual);
    }

    @Test
    void writeToFile_invalidData_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriterService.writeToFile(REPORT, WRONG_PATH));
    }

    @Test
    void writeToFile_nullData_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriterService.writeToFile(null, VALID_FILE_PATH));
    }
}
