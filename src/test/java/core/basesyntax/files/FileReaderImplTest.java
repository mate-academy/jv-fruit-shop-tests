package core.basesyntax.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String FILE_NAME = "temp.csv";
    private static Path path;
    private static FileReader fileReader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        path = Paths.get(FILE_NAME);
        fileReader = new FileReaderImpl();
        Files.deleteIfExists(path);
    }

    @Test
    public void readFromFile_Ok() throws IOException {
        Files.createFile(path);
        List<String> lines = List.of(
                "type,name,quantity",
                "b,banana,100",
                "b,apple,10",
                "s,banana,100",
                "p,banana,1");
        for (String line : lines) {
            Files.writeString(path, line + "\n", StandardOpenOption.APPEND);
        }
        List<String> linesFromFile = fileReader.readFromFile(FILE_NAME);
        Assert.assertEquals("Test failed! Expected size of List: " + lines.size(),
                lines.size(), linesFromFile.size());
        for (int i = 0; i < linesFromFile.size(); i++) {
            Assert.assertEquals("Test failed! The line №: " + i + " is not as expected",
                    lines.get(i), linesFromFile.get(i));
        }
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_NotOk() throws IOException {
        fileReader.readFromFile(FILE_NAME);
    }

    @After
    public void tearDown() throws Exception {
        Files.deleteIfExists(path);
    }
}
