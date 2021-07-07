package writer;

import org.junit.Test;

import java.io.*;
import java.nio.file.Path;
import static org.junit.Assert.*;

public class WriterToFileImplTest {
    private static final Writer<File, String> writer = new WriterToFileImpl();

    @Test
    public void writeToFile_Ok() {
        Path path = Path.of("src/test/resources/Result.csv");
        String expected = "datadatadata";
        writer.write(path.toFile(), expected);
        String result;
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            result = reader.readLine();
        } catch(IOException e) {
            throw new RuntimeException("There is no source like this");
        }
        assertEquals(expected, result);
    }

    @Test(expected = RuntimeException.class)
    public void badWriteToFile_Ok() {
        Path path = Path.of("src/test/resources3/Result.csv");
        writer.write(path.toFile(), "data");
    }
}