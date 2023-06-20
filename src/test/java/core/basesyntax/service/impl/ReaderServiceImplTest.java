package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;
import core.basesyntax.service.TransactionException;
import java.io.File;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static final String EXCEPTION_MESSAGE
            = TransactionException.class.toString();
    private static final String EMPTY_CSV = "src/test/resources/empty.csv";
    private static final String INCORRECT_FILE_PATH_FORMAT = "src/test/resources/input.mp3";
    private static final String INPUT_CSV_WITH_CONTENT = "src/test/resources/input.csv";
    private static ReaderService readerService;

    @BeforeAll
    static void beforeAll() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void reader_nullFilePath_notOk() {
        Assertions.assertThrows(TransactionException.class,
                () -> readerService.readFromFile(null),
                String.format("%s should throw when filePath is null", EXCEPTION_MESSAGE));
    }

    @Test
    void reader_validData_Ok() {
        Assertions.assertNotNull(readerService.readFromFile(INPUT_CSV_WITH_CONTENT));
    }

    @Test
    void reader_emptyData_notOk() {
        Assertions.assertThrows(TransactionException.class,
                () -> readerService.readFromFile(EMPTY_CSV),
                String.format("%s should throw when input file is empty", EXCEPTION_MESSAGE));
    }

    @Test
    void reader_validFileFormat_notOk() {
        Assertions.assertThrows(TransactionException.class,
                () -> readerService.readFromFile(INCORRECT_FILE_PATH_FORMAT),
                String.format("%s should throw when file have incorrect format",
                        EXCEPTION_MESSAGE));
    }

    @Test
    void reader_validFilePath_Ok() {
        readerService.readFromFile(INPUT_CSV_WITH_CONTENT);
    }

    @Test
    void reader_fileCreatedSuccessfully_Ok() {
        readerService.readFromFile(INPUT_CSV_WITH_CONTENT);
        File file = new File(INPUT_CSV_WITH_CONTENT);
        Assertions.assertTrue(file.exists());
    }

    @Test
    void reader_returnCorrectData_notOk() {
        List<String> actual = List.of("c,car,blue", "s,truck,green", "m,plane,320");
        List<String> expected = readerService.readFromFile(INPUT_CSV_WITH_CONTENT);
        Assertions.assertNotEquals(expected, actual);
    }
}
