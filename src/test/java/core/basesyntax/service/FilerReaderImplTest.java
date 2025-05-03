package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.FileReaderServiceImpl;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FilerReaderImplTest {
    private static final String VALID_FILE_PATH = "src/test/resources/data.csv";
    private static final String NOT_VALID_FILE_PATH = "src/main/resources/noSuchFile.csv";
    private static FileReaderService fileReaderService;
    private static List<String> dataFromList;

    @BeforeClass
    public static void beforeAll() {
        fileReaderService = new FileReaderServiceImpl();
        dataFromList = List.of("type,fruit,quantity", "b,banana,20", "b,apple,100", "s,banana,100",
                "p,banana,13", "r,apple,10", "p,apple,20", "p,banana,5", "s,banana,50");
    }

    @Test(expected = RuntimeException.class)
    public void fileNotExist_notOk() {
        fileReaderService.readFromFile(NOT_VALID_FILE_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void nullFilePath_notOk() {
        fileReaderService.readFromFile(null);
    }

    @Test
    public void readFile_ok() {
        List<String> actual = fileReaderService.readFromFile(VALID_FILE_PATH);
        assertEquals(dataFromList, actual);
    }
}
