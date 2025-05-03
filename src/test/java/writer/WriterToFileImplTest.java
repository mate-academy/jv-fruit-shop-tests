package writer;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import org.junit.Test;

public class WriterToFileImplTest {
    private static final Writer<File, String> writer = new WriterToFileImpl();
    private static final Path GOOD_PATH = Path.of("src/test/resources/Result.csv");
    private static final Path BAD_PATH = Path.of("src/test/resources3/Result.csv");

    @Test
    public void writeToFile_Ok() {
        String expected = "datadatadata";
        writer.write(GOOD_PATH.toFile(), expected);
        String result;
        try (BufferedReader reader = new BufferedReader(new FileReader(GOOD_PATH.toFile()))) {
            result = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("There is no source like this");
        }
        assertEquals(expected, result);
    }

    @Test(expected = RuntimeException.class)
    public void badWriteToFile_Ok() {
        writer.write(BAD_PATH.toFile(), "data");
    }
}
