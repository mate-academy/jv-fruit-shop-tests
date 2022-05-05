package service.impl;

import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import service.ReaderService;

public class ReaderServiceImplTest extends ReaderServiceImpl {
    private static final String CORRECT_FILE_PATH = "src/test/resources/input.csv";
    private static final String INCORRECT_FILE_PATH = "input.csv";
    private static ReaderService readerService;

    @BeforeClass
    public static void seUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void read_correctFilePath_ok() {
        readerService.read(CORRECT_FILE_PATH);
    }

    @Test
    public void read_readFromFile_ok() {
        List<String> expectedData = List.of("b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        List<String> actualData = readerService.read(CORRECT_FILE_PATH);
        Assert.assertEquals(expectedData, actualData);
    }

    @Test(expected = RuntimeException.class)
    public void read_incorrectFilePath_notOk() {
        read(INCORRECT_FILE_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void read_NullPath_NotOk() {
        readerService.read(null);
    }
}
