package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.ReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String VALID_PATH = "src/main/resources/input.csv";
    private static final String INVALID_PATH = "src/main/resources/invalid.csv";
    private static final String PATH_WITH_EMPTY_DATA = "src/main/resources/empty.csv";
    private ReaderService readerService;

    @Before
    public void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readDataFromFile_pathToFileIsValid_ok() {
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20",
                "b,apple,100", "s,banana,100", "p,banana,13", "r,apple,10",
                "p,apple,20", "p,banana,5", "s,banana,50");
        List<String> actual = readerService.readDataFromFile(VALID_PATH);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void readDataFromFile_pathToFileIsInvalid_notOk() {
        readerService.readDataFromFile(INVALID_PATH);
        fail("You must throw Runtime Exception, if the path to file does not exist");
    }

    @Test
    public void readDataFromFile_dataInTheFileIsEmpty_Ok() {
        List<String> expected = new ArrayList<>();
        List<String> actual = readerService.readDataFromFile(PATH_WITH_EMPTY_DATA);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void readDataFromFile_pathToFileIsNull_notOk() {
        readerService.readDataFromFile(null);
        fail("You must throw Runtime Exception, if the path to file is null");
    }
}
