package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileServiceImplTest {
    private static FileService fileService;
    private List<String> expectedList;

    @BeforeClass
    public static void beforeClass() {
        fileService = new FileServiceImpl();
    }

    @Test
    public void read_readFromFileWithData_Ok() {
        expectedList = List.of("b,orange,20", "b,banana,70",
                "b,apple,100", "s,orange,50", "p,orange,20",
                "s,banana,100", "p,banana,13", "r,apple,10",
                "p,apple,20", "p,banana,5", "s,banana,50", "s,banana,43");
        List<String> actualList = fileService.read("src/test/java/resources/FileWithData.csv");
        assertEquals(expectedList, actualList);
    }

    @Test
    public void read_readFromEmptyFile_Ok() {
        expectedList = new ArrayList<>();
        List<String> actualList = fileService.read("src/test/java/resources/EmptyFile.csv");
        assertEquals(expectedList, actualList);
    }

    @Test(expected = RuntimeException.class)
    public void readFromNotExistingPath_notOk() {
        fileService.read("src/test/java/resources/NotExistingFile.csv");
    }

    @Test
    public void write_writeStringToFile_Ok() {
        String data = "Hello, world!";
        fileService.write("src/test/java/resources/NewFile.csv", data);
        String actual;
        try {
            actual = Files.readString(Path.of("src/test/java/resources/NewFile.csv"));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file", e);
        }
        assertEquals(data, actual);
    }

    @Test(expected = RuntimeException.class)
    public void write_writeNullStringToFile_notOk() {
        String nullString = null;
        fileService.write("src/test/java/resources/NewFile.csv", nullString);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        File file = new File("src/test/java/resources/report.csv");
        file.delete();
    }
}
