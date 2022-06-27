package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String TEST_FILE =
            "src/test/resources/testFile.csv";
    private static final String EMPTY_FILE =
            "src/test/resources/emptyFile.csv";
    private static final String NOT_EXISTING_FILE =
            "src/test/resources/notExistingFile.csv";
    private static FileReader reader;

    @BeforeClass
    public static void beforeClass() {
        reader = new FileReaderImpl();
    }

    @Test
    public void readFromFile_emptyFile_ok() {
        List<String> expected = new ArrayList<>();
        List<String> actual = reader.readFromFile(Paths.get(EMPTY_FILE));
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_notExistingFile_notOk() {
        reader.readFromFile(Paths.get(NOT_EXISTING_FILE));
    }

    @Test
    public void readFromFile_file_ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,apple,50");
        expected.add("s,apple,25");
        List<String> actual = reader.readFromFile(Paths.get(TEST_FILE));
        assertEquals(expected, actual);
    }
}
