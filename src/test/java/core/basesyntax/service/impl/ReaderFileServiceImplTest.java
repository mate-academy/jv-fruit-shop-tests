package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderFileService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReaderFileServiceImplTest {
    private static ReaderFileService readerFileService;
    private static final String WRONG_PATH = "WRONG_PATH";
    private static final String INPUT_PATH_FILE
            = "src/main/resources/InputData.csv";
    private static final String TITLE = "type,fruit,quantity";
    private static final String BALANCE = "b,banana,20";
    private static final String RETURN = "r,apple,20";
    private static final String SUPPLY = "s,banana,100";
    private static final String PURCHASE = "p,apple,10";
    private static List<String> dataFromFile;

    @BeforeAll
    static void beforeAll() {
        readerFileService = new ReaderFileServiceImpl();
    }

    @BeforeEach
    void setUp() {
        dataFromFile = List.of(TITLE,BALANCE,RETURN,SUPPLY,PURCHASE);
    }

    @Test
    void read_pathIsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> readerFileService.read(null));
    }

    @Test
    void read_pathIsWrong_NotOk() {
        assertThrows(RuntimeException.class, () -> readerFileService.read(WRONG_PATH));
    }

    @Test
    void read_pathIsCorrect_Ok() {
        assertEquals(dataFromFile,readerFileService.read(INPUT_PATH_FILE));
    }
}
