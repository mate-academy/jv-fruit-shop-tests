package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String CORRECT_FILE_PATH = "src/test/resources/Input.csv";
    private static final String WRONG_FILE_PATH = "src/testM/resources/Input.csv";
    private static final String PATH_TO_EMPTY_FILE = "src/test/resources/empty.csv";
    private ReaderService readerService;

    @Before
    public void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_notOK() {
        readerService.readFromFile(WRONG_FILE_PATH);
    }

    @Test
    public void readFromFile_ok() {
        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        );
        List<String> actual = readerService.readFromFile(CORRECT_FILE_PATH);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFrom_EmptyFile_notOk() {
        readerService.readFromFile(PATH_TO_EMPTY_FILE);
    }

    @Test(expected = RuntimeException.class)
    public void readFile_ArgumentNull_notOk() {
        readerService.readFromFile(null);
    }
}
