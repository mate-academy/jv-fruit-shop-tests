package core.basesyntax.impl;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReaderServiceImplTest {
    private static ReaderService readerService;
    private static final String EMPTY_FILE_PATH = "";
    private static final String PATH_TO_EMPTY_FILE =
            "src/test/resources/emptyFileForRead.csv";
    private static final String PATH_TO_READ_FILE =
            "src/test/resources/database.csv";

    @BeforeAll
    static void beforeAll() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void readerService_emptyAddress_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> readerService.fileReader(EMPTY_FILE_PATH));
    }

    @Test
    void readerService_emptyFile_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> readerService.fileReader(PATH_TO_EMPTY_FILE));
    }

    @Test
    void readerService_normalFile_Ok() {
        List<String> dataFromFile = readerService.fileReader(PATH_TO_READ_FILE);
        List<String> expected = List.of(
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        Assertions.assertEquals(expected, dataFromFile);
    }
}
