package core.basesyntax.service.impl;

import core.basesyntax.service.FileReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static String inputPath;
    private static String emptyFile;
    private static FileReaderService fileReaderService;
    private static List<String> lines;

    @BeforeClass
    public static void beforeClass() {
        emptyFile = "src/test/java/resourcesTest/empty.csv";
        lines = new ArrayList<>();
        lines.add("type,fruit,quantity");
        lines.add("b,banana,20");
        lines.add("b,apple,100");
        lines.add("p,banana,5");
        lines.add("s,banana,50");
        fileReaderService = new FileReaderServiceImpl();
        inputPath = "src/test/java/resourcesTest/inputFileTest.csv";
        emptyFile = "src/test/java/resourcesTest/empty.csv";
    }

    @Test
    public void read_file_ok() {
        List<String> actual = fileReaderService.readFile(inputPath);
        List<String> expected = lines;
        Assert.assertNotNull(actual);
        Assert.assertEquals("Expected: " + expected + ", but " + actual, expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void read_file_NullPath_notOk() {
        fileReaderService.readFile(null);
    }

    @Test
    public void read_file_emptyFile_ok() {
        List<String> actual = fileReaderService.readFile(emptyFile);
        Assert.assertNotNull(actual);
        Assert.assertTrue(actual.isEmpty());
    }

    @Test(expected = RuntimeException.class)
    public void read_file_emptyPath_notOk() {
        fileReaderService.readFile("");
    }
}
