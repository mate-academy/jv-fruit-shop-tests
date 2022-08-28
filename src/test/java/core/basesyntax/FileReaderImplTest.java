package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.FileReader;
import core.basesyntax.service.impl.FileReaderImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String NON_EXISTING_FILE = "nonexisting.csv";
    private static final String TEST_FILE = "src/test/resources/inputs/test.csv";
    private static final String EMPTY_FILE = "src/test/resources/inputs/empty.csv";

    private FileReader fileReader;

    @Before
    public void setup() {
        fileReader = new FileReaderImpl();
    }

    @Test(expected = RuntimeException.class)
    public void readFile_fromNonExistingFile_notOk() {
        fileReader.readFile(NON_EXISTING_FILE);
    }

    @Test
    public void readFile_TestInput_Ok() {
        List<String> expected = readAllLines(TEST_FILE);
        List<String> actual = fileReader.readFile(TEST_FILE);
        assertEquals(expected, actual);
    }

    @Test
    public void readFile_FromEmptyFile_Ok() {
        List<String> linesFromFile = fileReader.readFile(EMPTY_FILE);
        assertTrue(linesFromFile.isEmpty());
    }

    private static List<String> readAllLines(String path) {
        List<String> lines;
        try {
            lines = Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file", e);
        }
        return lines;
    }
}
