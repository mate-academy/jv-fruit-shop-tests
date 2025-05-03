package core.basesyntax.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static final List<String> LINES = List.of(
            "type,name,quantity",
            "b,banana,100",
            "b,apple,10",
            "s,banana,100",
            "p,banana,1");

    private static FileWriter fileWriter;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fileWriter = new FileWriterImpl();
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_NotOK() throws IOException {
        String wrongName = "src/temp/temp.csv";
        fileWriter.writeToFile(wrongName, LINES);
    }

    @Test
    public void writeToFile_OK() throws IOException {
        String fileName = "temp.csv";
        Path path = Paths.get(fileName);
        File file = new File(fileName);
        fileWriter.writeToFile(fileName, LINES);
        Assert.assertTrue("Test failed! File: " + fileName + " - doesn't exist",
                Files.exists(path));
        List<String> linesFromFile = Files.readAllLines(file.toPath());
        Assert.assertEquals("Test failed! Expected size of List: " + LINES.size(),
                LINES.size(), linesFromFile.size());
        for (int i = 0; i < linesFromFile.size(); i++) {
            Assert.assertEquals("Test failed! The line №: " + i + " is not as expected",
                    LINES.get(i), linesFromFile.get(i));
        }
    }

    @AfterClass//I want after the tests all the garbage in the form of test files was removed !!!
    public static void afterClass() throws Exception {
        String fileName = "temp.csv";
        Path path = Paths.get(fileName);
        Files.deleteIfExists(path);
    }
}
