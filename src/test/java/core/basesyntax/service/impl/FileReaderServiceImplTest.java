package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.service.FileReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private final FileReaderService fileReaderService = new FileReaderServiceImpl();

    @Test
    public void readFile_ok() {
        List<String> exceptedFile = new ArrayList<>();
        exceptedFile.add("type,fruit,quantity");
        exceptedFile.add("b,banana,20");
        exceptedFile.add("b,apple,100");
        exceptedFile.add("s,banana,100");
        exceptedFile.add("p,banana,13");
        exceptedFile.add("r,apple,10");
        exceptedFile.add("p,apple,20");
        exceptedFile.add("p,banana,5");
        exceptedFile.add("s,banana,50");

        List<String> actualFile = fileReaderService.readFromFile("src/main/resources/file.csv");
        assertEquals(exceptedFile,actualFile);
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

    @Test
    public void readFileWithWrongType_NotOk() {
        List<String> exceptedFile = new ArrayList<>();
        exceptedFile.add("type,fruit,quantity");
        exceptedFile.add("w,banana,20");
        exceptedFile.add("b,apple,100");
        exceptedFile.add("s,bananas,1100");
        exceptedFile.add("p,banana,13");
        exceptedFile.add("r,applse,10");
        exceptedFile.add("p,apple,20");
        exceptedFile.add("p,banana,5");
        exceptedFile.add("s,banana,50");

        List<String> actualFile = fileReaderService.readFromFile("src/main/resources/file.csv");
        assertNotEquals(exceptedFile,actualFile);
    }

    @Test
    public void readFileWithWrongFruit_NotOk() {
        List<String> exceptedFile = new ArrayList<>();
        exceptedFile.add("type,fruit,quantity");
        exceptedFile.add("b,banana,20");
        exceptedFile.add("b,apple,100");
        exceptedFile.add("s,bananas,1100");
        exceptedFile.add("p,banana,13");
        exceptedFile.add("r,applse,10");
        exceptedFile.add("p,apple,20");
        exceptedFile.add("p,banana,5");
        exceptedFile.add("s,banana,50");

        List<String> actualFile = fileReaderService.readFromFile("src/main/resources/file.csv");
        assertNotEquals(exceptedFile,actualFile);
    }

    @Test
    public void readFileWithWrongQuantity_NotOk() {
        List<String> exceptedFile = new ArrayList<>();
        exceptedFile.add("type,fruit,quantity");
        exceptedFile.add("b,banana,20");
        exceptedFile.add("b,apple,100");
        exceptedFile.add("s,banana,1100");
        exceptedFile.add("p,banana,13");
        exceptedFile.add("r,apple,10");
        exceptedFile.add("p,apple,20");
        exceptedFile.add("p,banana,5");
        exceptedFile.add("s,banana,50");

        List<String> actualFile = fileReaderService.readFromFile("src/main/resources/file.csv");
        assertNotEquals(exceptedFile,actualFile);
    }

    @Test
    public void readFileWithWrongName_NotOk() {
        List<String> exceptedFile = new ArrayList<>();
        exceptedFile.add("type,fruit,quantity");
        exceptedFile.add("b,banana,20");
        exceptedFile.add("b,apple,100");
        exceptedFile.add("s,banaana,100");
        exceptedFile.add("p,banana,13");
        exceptedFile.add("r,apple,10");
        exceptedFile.add("p,apple,20");
        exceptedFile.add("p,banana,5");
        exceptedFile.add("s,banana,50");

        List<String> actualFile = fileReaderService.readFromFile("src/main/resources/file.csv");
        assertNotEquals(exceptedFile,actualFile);
    }
}
