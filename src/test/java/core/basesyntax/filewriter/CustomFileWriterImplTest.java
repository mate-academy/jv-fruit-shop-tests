package core.basesyntax.filewriter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.file.CustomFileWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CustomFileWriterImplTest {
    private Path tempFile;
    private CustomFileWriterImpl fileWriter;

    @Before
    public void setUp() throws IOException {
        fileWriter = new CustomFileWriterImpl();
        tempFile = Files.createTempFile("test-file-writer", ".csv");
    }

    @After
    public void tearDown() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    @Test
    public void write_validData_writesToFileCorrectly() throws IOException {
        String testData = String.join(System.lineSeparator(),
                "fruit,quantity",
                "banana,10",
                "apple,20");

        fileWriter.write(testData, tempFile.toString());

        List<String> lines = Files.readAllLines(tempFile);

        assertEquals(3, lines.size());
        assertEquals("fruit,quantity", lines.get(0));
        assertEquals("banana,10", lines.get(1));
        assertEquals("apple,20", lines.get(2));
    }

    @Test
    public void write_createsNonExistingFile() throws IOException {
        Path newFile = tempFile.getParent().resolve("new-file.csv");
        String data = "hello,world";

        fileWriter.write(data, newFile.toString());

        assertTrue(Files.exists(newFile));

        List<String> lines = Files.readAllLines(newFile);
        String actual = String.join(System.lineSeparator(), lines);
        assertEquals(data, actual);

        Files.deleteIfExists(newFile);
    }
}
