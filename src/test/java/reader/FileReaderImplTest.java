package reader;

import org.junit.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.junit.Assert.*;

public class FileReaderImplTest {
    private static Reader<String, Path> reader = new FileReaderImpl();

    @Test
    public void readFromExistSource_Ok() {
        List<String> readedData = reader.read(Path.of("src/test/resources/CorrectTestSource.csv"));
        List<String> correctReadedData;
        try {
            correctReadedData = Files.readAllLines(Path.of("src/test/resources/CorrectTestSource.csv"));
        } catch (IOException e) {
            throw new RuntimeException("There is no source like this");
        }
        assertArrayEquals(correctReadedData.toArray(), readedData.toArray());
    }

    @Test(expected = RuntimeException.class)
    public void readFromNonExistSource_NotOk() {
        List<String> readedData = reader.read(Path.of("src/test/resources2/CorrectTestSource.csv"));
    }
}