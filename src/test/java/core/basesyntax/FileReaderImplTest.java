package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.FileReader;
import core.basesyntax.service.impl.FileReaderImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String NON_EXISTING_FILE = "nonexisting.csv";
    private static final String TRANSACTIONS_FILE = "src/test/resources/inputs/transaction.csv";
    private static final String TEST_FILE = "src/test/resources/inputs/test.csv";
    private static final String EMPTY_FILE = "src/test/resources/inputs/empty.csv";

    private FileReader fileReader;

    @Before
    public void setup() {
        fileReader = new FileReaderImpl();
    }

    @After
    public void close() {
    }

    @Test(expected = RuntimeException.class)
    public void readFile_fromNonExistingFile_notOk() {
        fileReader.readFile(NON_EXISTING_FILE);
    }

    @Test
    public void readFromFile_ok() {
        List<String> expected = readAllLines(TRANSACTIONS_FILE);
        List<String> actual = fileReader.readFile(TRANSACTIONS_FILE);
        assertTrue(actual.size() == 10);
        for (int i = 0; i < 10; i++) {
            assertEquals(expected.get(i), actual.get(i));
        }
        actual = fileReader.readFile(TEST_FILE);
        assertTrue(actual.size() == 10);
        for (int i = 1; i <= 10; i++) {
            assertEquals("Line " + i + ",",
                    actual.get(i - 1));
        }
    }

    @Test
    public void readEmptyFromFile_ok() {
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
