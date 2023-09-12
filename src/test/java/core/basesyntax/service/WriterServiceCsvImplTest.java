package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.WriterServiceCsvImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WriterServiceCsvImplTest {
    private static final String VALID_FILE = "src/test/resources/validOutput.csv";
    private static final String INVALID_FILE = "nnnn/file.csv";
    private static final List<String> LINES = new ArrayList<>(List.of(
            "type,fruit,quantity",
            "b,banana,20"));
    private final WriterService writerService = new WriterServiceCsvImpl();

    @Test
    void writeToFile_validFile_ok() {
        writerService.writeToFile(LINES, VALID_FILE);
        Assertions.assertLinesMatch(LINES, readFromFile(VALID_FILE));
    }

    @Test
    void writeToFile_invalidFile_notOk() {
        RuntimeException invalidFileException = assertThrows(RuntimeException.class,
                () -> writerService.writeToFile(LINES, INVALID_FILE));
        assertEquals("Can't write to file: " + INVALID_FILE, invalidFileException.getMessage());
    }

    private List<String> readFromFile(String filePath) {
        List<String> strings;
        try {
            strings = Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return strings;
    }
}
