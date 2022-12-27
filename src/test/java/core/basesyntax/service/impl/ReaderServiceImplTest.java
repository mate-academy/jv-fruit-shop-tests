package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ReaderServiceImplTest {
    public static final String TEST_FILEPATH = "src/test/resources/input.csv";
    public static final String WRONG_FILEPATH = "/var/lib";
    private ReaderService readerService;

    @Before
    public void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readFromFile_Work_Ok() {
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20", "b,apple,100",
                "s,banana,100", "p,banana,13", "r,apple,10", "p,apple,20", "p,banana,5",
                "s,banana,50");
        List<String> actual = readerService.readFromFile(TEST_FILEPATH);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_Exception_NotOk() {
        readerService.readFromFile(WRONG_FILEPATH);
    }
}
