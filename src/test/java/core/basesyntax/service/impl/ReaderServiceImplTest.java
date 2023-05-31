package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String CORRECT_FILE_PATH = "src/test/resources/inputdata-test.csv";
    private static final String WRONG_FILE_PATH = "src/test/resources/wrong.csv";
    private static final List<String> DATA = List.of("type,fruit,quantity",
            "b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50");
    private ReaderService readerService;

    @Before
    public void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readFromFile_Ok() {
        List<String> expected = DATA;
        List<String> actual = readerService.readFromFile(CORRECT_FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_NotOk() {
        readerService.readFromFile(WRONG_FILE_PATH);
    }
}
