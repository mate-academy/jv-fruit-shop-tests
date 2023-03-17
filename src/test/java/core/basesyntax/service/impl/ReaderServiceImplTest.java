package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReaderServiceImplTest {
    private static final String HEADER = "type,fruit,quantity";
    private static final String LINE_OK = "b,banana,100";
    private static final String PATH_TO_EMPTY = "src/test/resources/empty.txt";
    private static final String PATH_TO_FRUITS = "src/test/resources/fruits.txt";
    private static final String WRONG_PATH = " ";
    private static ReaderService readerService;

    @BeforeAll
    static void beforeAll() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void read_emptyFile_isOk() {
        List<String> listOfDataFromFile = readerService.read(PATH_TO_EMPTY);
        assertEquals(new ArrayList<>(), listOfDataFromFile);
    }

    @Test
    void reade_data_isOk() {
        List<String> listOfDataFromFile = readerService.read(PATH_TO_FRUITS);
        assertEquals(List.of(HEADER,LINE_OK), listOfDataFromFile);
    }

    @Test
    void read_noFile_isNotOk() {
        assertThrows(RuntimeException.class,
                () -> readerService.read(WRONG_PATH));
    }
}
