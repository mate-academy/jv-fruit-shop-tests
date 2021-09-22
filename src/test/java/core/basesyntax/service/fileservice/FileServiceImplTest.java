package core.basesyntax.service.fileservice;


import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileServiceImplTest {
    private static FileService fileService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fileService = new FileServiceImpl();
    }

    @Test
    public void readDataFromFile_correctPath_Ok() throws IOException {
        List<String> expected = Files
                .readAllLines(Path.of("src/test/resources/input.csv"));
        List<String> actual = fileService.readDataFromFile("src/test/resources/input.csv");
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readDataFromFile_incorrectPath_NotOk() {
        String incorrectPath = "src/java/resources/input.csv";
        fileService.readDataFromFile(incorrectPath);
    }

    @Test
    public void writeDataToFile_Ok() throws IOException {
        String data = "fruit,quantity\n"
                + "banana,150\n"
                + "apple,150";
        fileService.writeDataToFile("src/test/resources/output.csv", data);
        List<String> expected = new ArrayList<>();
        expected.add("fruit,quantity");
        expected.add("banana,150");
        expected.add("apple,150");
        List<String> actual = fileService.readDataFromFile("src/test/resources/output.csv");
        Assert.assertEquals(expected, actual);
    }

    @After
    public void close() throws IOException {
        new FileWriter("src/test/resources/output.csv", false).close();
    }

}