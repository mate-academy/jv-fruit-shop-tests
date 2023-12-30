package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.ReaderServiceCsvImpl;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReaderServiceCsvImplTest {
    private static final Path INVALID_FILE_PATH =
            Path.of("INVALID_FILE.qwerty");
    private static final Path CORRECT_FILE_PATH =
            Path.of("src/test/resources/testReadFileContent.csv");
    private static final Path EMPTY_FILE_PATH =
            Path.of("src/test/resources/testReadEmptyFile.csv");
    private static final String CORRECT_FILE_EXPECTED_RESULT =
            "qwerty,1" + System.lineSeparator();
    private static final String EMPTY_FILE_EXPECTED_RESULT = "";
    private ReaderServiceCsvImpl readerService;

    @BeforeEach
    public void setUp() {
        readerService = new ReaderServiceCsvImpl();
    }

    @Test
    public void readData_nullData_notOk() {
        assertThrows(RuntimeException.class, () -> readerService.readData(null));
    }

    @Test
    public void readData_invalidFile_notOk() {
        assertThrows(RuntimeException.class,
                () -> readerService.readData(INVALID_FILE_PATH.toString()));
    }

    @Test
    public void readData_contentFile_ok() {
        assertEquals(CORRECT_FILE_EXPECTED_RESULT,
                readerService.readData(CORRECT_FILE_PATH.toString()));
    }

    @Test
    public void readData_emptyFile_ok() {
        assertEquals(EMPTY_FILE_EXPECTED_RESULT,
                readerService.readData(EMPTY_FILE_PATH.toString()));
    }
}
