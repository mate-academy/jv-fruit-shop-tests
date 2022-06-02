package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private final FileReaderService fileReaderService = new FileReaderServiceImpl();

    @Test
    public void readFile_ok() {
        List<String> excepted = new ArrayList<>();
        excepted.add("type,fruit,quantity");
        excepted.add("b,banana,20");
        excepted.add("b,apple,100");
        excepted.add("s,banana,100");
        excepted.add("p,banana,13");
        excepted.add("r,apple,10");
        excepted.add("p,apple,20");
        excepted.add("p,banana,5");
        excepted.add("s,banana,50");

        List<String> actual = fileReaderService.readFromFile("src/main/resources/file.csv");
        assertEquals(excepted,actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFile_WrongFormatFile_notOk() {
        fileReaderService.readFromFile("src/main/resources/file.css");
    }

    @Test(expected = RuntimeException.class)
    public void readFile_NullFile_notOk() {
        fileReaderService.readFromFile(null);
    }

    @Test(expected = RuntimeException.class)
    public void readFile_WrongNameFile_notOk() {
        fileReaderService.readFromFile("src/main/resources/file1.csv");
    }

    @Test(expected = RuntimeException.class)
    public void readFile_WrongPathToFile_notOk() {
        fileReaderService.readFromFile("src/resources/file.csv");
    }
}
