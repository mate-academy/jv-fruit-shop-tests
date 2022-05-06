package core.basesyntax.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String fileName = "temp.csv";
    private static final String wrongName = "src/temp/temp.csv";
    private static final Path path = Paths.get(fileName);
    private static final FileWriter fileWriter = new FileWriterImpl();
    private static final List<String> lines = List.of(
            "type,name,quantity",
            "b,banana,100",
            "b,apple,10",
            "s,banana,100",
            "p,banana,1");

    @Test(expected = RuntimeException.class)
    public void writeToFile_NotOK() throws IOException {
        fileWriter.writeToFile(wrongName, lines);
    }

    @Test
    public void writeToFile_OK() throws IOException {
        File file = new File(fileName);
        fileWriter.writeToFile(fileName, lines);
        Assert.assertTrue(Files.exists(path));
        List<String> linesFromFile = Files.readAllLines(file.toPath());
        for (int i = 0; i < linesFromFile.size(); i++) {
            Assert.assertEquals(lines.get(i), linesFromFile.get(i));
        }
        Files.deleteIfExists(path);
    }
}
