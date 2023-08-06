package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.ReaderServiceCsvImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReaderServiceCsvImplTest {
    private static final String VALID_FILE = "src/test/resources/validInput.csv";
    private static final String INVALID_FILE = "file.csv";
    private ReaderService readerService;

    @BeforeEach
    void setUp() {
        readerService = new ReaderServiceCsvImpl();
    }

    @Test
    void readFromFile_validFile_Ok() {
        List<String> expectedStrings = new ArrayList<>(List.of(
                "type,fruit,quantity",
                "b,banana,20"));
        List<String> actualStrings = readerService.readFromFile(VALID_FILE);
        Assertions.assertLinesMatch(expectedStrings, actualStrings);
    }

    @Test
    void readFromFile_invalidFile_notOk() {
        RuntimeException invalidFileException = assertThrows(RuntimeException.class,
                () -> readerService.readFromFile(INVALID_FILE));
        assertEquals("Can't find file by path: " + INVALID_FILE, invalidFileException.getMessage());
    }
}
