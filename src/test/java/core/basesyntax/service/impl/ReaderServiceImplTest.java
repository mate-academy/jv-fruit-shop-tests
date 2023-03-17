package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.ReaderService;
import java.util.ArrayList;
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
        dataFileList = new ArrayList<>();

        dataFileList.add("type,fruit,quantity");
        dataFileList.add("b,banana,20");
        dataFileList.add("b,apple,100");
        dataFileList.add("s,banana,100");
        dataFileList.add("p,banana,13");
        dataFileList.add("r,apple,10");
        dataFileList.add("p,apple,20");
        dataFileList.add("p,banana,5");
        dataFileList.add("s,banana,50");
    }

    @Test(expected = RuntimeException.class)
    public void fileNotExist_notOk() {
        readerService.readFromFile(FILE_PATH_NOT_OK);
        fail(FILE_PATH_NOT_OK + RuntimeException.class
                + " exception be thrown for not existing file, but it wasn't");
    }

    @Test(expected = RuntimeException.class)
    public void nullFilePath_notOk() {
        readerService.readFromFile(null);
        fail(RuntimeException.class
                + " exception be thrown for not existing file, but it wasn't");
    }

    @Test
    public void readFile_ok() {
        List<String> actual = readerService.readFromFile(FILE_PATH_OK);
        assertEquals(dataFileList,actual);
    }
}
