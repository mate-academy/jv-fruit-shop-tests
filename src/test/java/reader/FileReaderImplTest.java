package reader;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Test;

public class FileReaderImplTest {
    private static Reader<String, Path> reader = new FileReaderImpl();
    private static final Path GOOD_PATH = Path.of("src/test/resources/CorrectTestSource.csv");
    private static final Path BAD_PATH = Path.of("src/test/resources3/Result.csv");

    @Test
    public void readFromExistSource_Ok() {
        List<String> readedData = reader.read(GOOD_PATH);
        List<String> correctReadedData;
        try {
            correctReadedData = Files.readAllLines(GOOD_PATH);
        } catch (IOException e) {
            throw new RuntimeException("There is no source like this");
        }
        assertEquals(correctReadedData, readedData);
    }

    @Test(expected = RuntimeException.class)
    public void readFromNonExistSource_NotOk() {
        List<String> readedData = reader.read(BAD_PATH);
    }
}
