package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String FILE_PATH_NOT_OK = "src/main/java/resources/fileNotExist.csv";
    private static final String FILE_PATH_OK = "src/main/java/resources/data.csv";
    private static List<String> dataFileList;
    private static ReaderService readerService;

    @BeforeClass
    public static void beforeAll() {
        readerService = new ReaderServiceImpl();
        dataFileList = List.of("type,fruit,quantity","b,banana,20", "b,apple,100", "s,banana,100",
                "p,banana,13", "r,apple,10","p,apple,20", "p,banana,5","s,banana,50");
    }

    @Test(expected = RuntimeException.class)
    public void fileNotExist_notOk() {
        readerService.readFromFile(FILE_PATH_NOT_OK);
    }

    @Test(expected = RuntimeException.class)
    public void nullFilePath_notOk() {
        readerService.readFromFile(null);
    }

    @Test
    public void readFile_ok() {
        List<String> actual = readerService.readFromFile(FILE_PATH_OK);
        assertEquals(dataFileList,actual);
    }
}
