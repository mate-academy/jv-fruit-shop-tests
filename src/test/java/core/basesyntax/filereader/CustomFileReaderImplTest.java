package core.basesyntax.filereader;

import static org.junit.Assert.assertEquals;

import core.basesyntax.file.CustomFileReaderImpl;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CustomFileReaderImplTest {
    private Path tempFile;
    private CustomFileReaderImpl fileReader;

    @Before
    public void setUp() throws IOException {
        fileReader = new CustomFileReaderImpl();
        tempFile = Files.createTempFile("test-file", ".csv");
        try (FileWriter writer = new FileWriter(tempFile.toFile())) {
            writer.write("type,fruit,quantity\n");
            writer.write("b,banana,20\n");
            writer.write("s,apple,50\n");
        }
    }

    @After
    public void tearDown() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    @Test
    public void read_validFile_returnsCorrectContent() {
        List<String> lines = fileReader.read(tempFile.toString());

        assertEquals(3, lines.size());
        assertEquals("type,fruit,quantity", lines.get(0));
        assertEquals("b,banana,20", lines.get(1));
        assertEquals("s,apple,50", lines.get(2));
    }
}
