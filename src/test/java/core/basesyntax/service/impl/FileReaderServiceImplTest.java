package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static FileReaderService fileReaderService;

    @BeforeClass
    public static void beforeClass() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_nullValue_NotOk() {
        fileReaderService.readFromFile(null);
    }

    @Test
    public void readFromFile_Ok() {
        String pathName = "src\\test\\resources\\testFile.csv";
        List<String> fruitList = new ArrayList<>();
        fruitList.add("b,apple,20");
        fruitList.add("s,banana,30");
        fruitList.add("r,apple,10");
        List<String> actual = fileReaderService.readFromFile(pathName);
        assertEquals(fruitList, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_notExistFile_NotOk() {
        String path = "src\\test\\resources\\testFile2.csv";
        fileReaderService.readFromFile(path);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_emptyPathLine_NotOk() {
        fileReaderService.readFromFile("");
    }
}
