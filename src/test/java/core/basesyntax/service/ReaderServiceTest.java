package core.basesyntax.service;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceTest {
    private static ReaderService readerService;
    private static final String NOT_VALID_FILE_PATH = "src/test/resources/notValidFile.csv";
    private static final String VALID_FILE_PATH = "src/test/resources/validFile.csv";
    private static final String EMPTY_FILE_PATH = "src/test/resources/emptyFile.csv";
    private static final String NOT_EXISTED_FILE = "src/test/resources/notExist.csv";

    @BeforeClass
    public static void beforeClass() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void inputDataFileFirst_Ok() {
        List<String> expected =
                List.of("some info",
                        "b,banana,10",
                        "not valid info");
        List<String> actual = readerService.readFromFile(NOT_VALID_FILE_PATH);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void inputDataFileSecond_Ok() {
        List<String> expected =
                List.of("type,fruit,quantity",
                        "b,banana,20",
                        "b,apple,100",
                        "s,banana,100",
                        "p,banana,13");
        List<String> actual = readerService.readFromFile(VALID_FILE_PATH);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void emptyInputData_Ok() {
        List<String> dataFromFile = readerService.readFromFile(EMPTY_FILE_PATH);
        int actual = dataFromFile.size();
        int expected = 0;
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void nullFilePath_NotOk() {
        readerService.readFromFile(null);
    }

    @Test(expected = RuntimeException.class)
    public void invalidInputDataPath_NotOk() {
        readerService.readFromFile(NOT_EXISTED_FILE);
    }
}
