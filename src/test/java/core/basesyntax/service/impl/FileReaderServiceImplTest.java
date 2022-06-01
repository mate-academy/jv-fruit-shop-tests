package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileReaderServiceImplTest {
    private static FileReaderService fileReaderService;

    @BeforeAll
    static void setUp() {
        fileReaderService = new FileReaderServiceImpl();
    }

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

    @Test
    public void readFile_WrongFormatFile_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileReaderService.readFromFile("src/main/resources/file.css"));
    }

    @Test
    public void readFile_NullFile_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileReaderService.readFromFile(null));
    }

    @Test
    public void readFile_WrongNameFile_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileReaderService.readFromFile("src/main/resources/file1.csv"));
    }

    @Test
    public void readFile_WrongPathToFile_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileReaderService.readFromFile("src/resources/file.csv"));
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
}
