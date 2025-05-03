package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;

import core.basesyntax.service.impl.CsvReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvReaderServiceTest {
    private static final String VALID_OUTPUT_PATH = "src/test/resources/CsvInputData.csv";
    private static final String NON_VALID_FILE = "nonValidFile.csv";
    private ReaderService readerService;

    @BeforeEach
    void setUp() {
        readerService = new CsvReaderService();
    }

    @Test
    void read_validFileData_ok() {
        List<String> expectedLines = new ArrayList<>(List.of("b,banana,20",
                "s,apple,100", "p,banana,10"));
        List<String> actualLines = readerService.read(VALID_OUTPUT_PATH);
        assertLinesMatch(expectedLines, actualLines, "Read data differ from expected.");
    }

    @Test
    void read_nonValidFile_notOk() {
        RuntimeException runtimeException = Assertions.assertThrows(
                RuntimeException.class, () -> readerService.read(NON_VALID_FILE));
        assertEquals(runtimeException.getMessage(),
                "Can't read the file: " + NON_VALID_FILE);
    }
}
