package core.basesyntax.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String fileName = "temp.csv";
    private static final Path path = Paths.get(fileName);
    private static final FileReader fileReader = new FileReaderImpl();

    @Test
    public void readFromFile_Ok() throws IOException {
        Files.deleteIfExists(path);
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
        List<String> linesFromFile = fileReader.readFromFile(fileName);
        for (int i = 0; i < linesFromFile.size(); i++) {
            Assert.assertEquals(lines.get(i), linesFromFile.get(i));
        }
        Files.deleteIfExists(path);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_NotOk() throws IOException {
        Files.deleteIfExists(path);
        fileReader.readFromFile(fileName);
    }
}
