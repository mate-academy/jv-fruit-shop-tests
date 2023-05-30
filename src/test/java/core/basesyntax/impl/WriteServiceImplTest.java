package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.WriteService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriteServiceImplTest {

    private static final String REPORT = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator()
            + "apple,90" + System.lineSeparator();
    private WriteService writeService;
    private final String invalidFilePath = "src/main/java/invalid/path/report.csv";

    @BeforeEach
    void setUp() {
        writeService = new WriteServiceImpl();
    }

    @Test
    void writeToFile_validData_ok() {
        writeService.writeToFile(REPORT,"src/main/java/report.csv");
        List<String> expected = List.of("fruit,quantity", "banana,152", "apple,90");
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of("src/main/java/report.csv"));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file", e);
        }
        assertEquals(expected, actual);
    }

    @Test
    void writeToFile_null_not_ok() {
        assertThrows(RuntimeException.class,
                () -> writeService.writeToFile(null, "src/main/java/report.csv"),
                "Report can't be null");
    }

    @Test
    void writetofile_invalid_path() {
        assertThrows(RuntimeException.class,
                () -> writeService.writeToFile(REPORT, invalidFilePath),
                "Failed to write to file");
    }
}
