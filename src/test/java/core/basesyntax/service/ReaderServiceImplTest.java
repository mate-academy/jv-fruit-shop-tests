package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReaderServiceImplTest {
    private static final String HEADER = "type,fruit,quantity";
    private static final String LINE_OK = "b,banana,100";
    private static final String PATH_TO_EMPTY = "src/test/java/core/basesyntax/empty.txt";
    private static final String PATH_TO_FRUITS = "src/test/java/core/basesyntax/fruits.txt";
    private static final String WRONG_PATH = " ";
    private static ReaderService readerService;

    @BeforeAll
    static void beforeAll() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void read_emptyFile_ok() {
        List<String> listOfDataFromFile = readerService.getListOfDataFromFile(PATH_TO_EMPTY);
        assertEquals(new ArrayList<>(), listOfDataFromFile);
    }

    @Test
    void read_Data_ok() {
        List<String> listOfDataFromFile = readerService.getListOfDataFromFile(PATH_TO_FRUITS);
        assertEquals(List.of(HEADER,LINE_OK), listOfDataFromFile);
    }

    @Test
    void read_No_File_notOk() {
        assertThrows(InvalidPathException.class,
                () -> readerService.getListOfDataFromFile(WRONG_PATH));
    }
}
