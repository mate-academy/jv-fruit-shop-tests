package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ReaderServiceImplTest {

    private static final String TEST_FILE_PATH = "src/test/resources/test_data.csv";
    private static final String WRONG_FILE_PATH = "src/test/abcd/test_data.csv";
    private ReaderService fileReader;

    @Before
    public void setUp() {
        fileReader = new ReaderServiceImpl();
    }

    @Test
    public void readFromFile_Ok() {
        List<String> expectedDataFromFile = List.of(
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        assertEquals(expectedDataFromFile, fileReader.readFromFile(TEST_FILE_PATH));
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_NotOk() {
        fileReader.readFromFile(WRONG_FILE_PATH);
    }
}
