package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String TEST_FILE = "test_file.txt";
    private static final String EMPTY_FILE = "empty_file.txt";
    private FileReader fileReader;

    @Before
    public void setUp() throws IOException {
        fileReader = new FileReaderImpl();
        try (FileWriter writer = new FileWriter(TEST_FILE)) {
            writer.write("Header line\n");
            writer.write("Line 1\n");
            writer.write("Line 2\n");
            writer.write("Line 3\n");
        }
        Files.createFile(Path.of(EMPTY_FILE));
    }

    @After
    public void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(TEST_FILE));
        Files.deleteIfExists(Path.of(EMPTY_FILE));
    }

    @Test
    public void readFile_validFile_returnsExpectedLines() {
        List<String> expectedLines = List.of("Line 1", "Line 2", "Line 3");
        List<String> actualLines = fileReader.readFile(TEST_FILE);
        assertEquals("The lines should match the expected output", expectedLines, actualLines);
    }

    @Test
    public void readFile_emptyFile_returnsEmptyList() {
        List<String> actualLines = fileReader.readFile(EMPTY_FILE);
        assertTrue("The list of lines should be empty", actualLines.isEmpty());
    }

    @Test
    public void readFile_fileNotFound_throwsRuntimeException() {
        assertThrows(RuntimeException.class,
                () -> fileReader.readFile("nonexistent_file.txt"));
    }
}
