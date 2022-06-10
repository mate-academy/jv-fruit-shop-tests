package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.Reader;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderImplTest {
    private static final String TEST_FILE =
            "src/test/resources/testFile.csv";
    private static final String EMPTY_FILE =
            "src/test/resources/emptyFile.csv";
    private static final String NOT_EXISTING_FILE =
            "src/test/resources/notExistingFile.csv";
    private static Reader reader;

    @BeforeClass
    public static void beforeClass() {
        reader = new ReaderImpl();
    }

    @Test(expected = RuntimeException.class)
    public void readNotExistingFile_notOk() {
        reader.readFromFile(NOT_EXISTING_FILE);
    }

    @Test
    public void read_emptyFile_ok() {
        List<String> expected = new ArrayList<>();
        List<String> actual = reader.readFromFile(EMPTY_FILE);
        assertEquals(expected, actual);
    }

    @Test
    public void readFile_ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("b,orange,20");
        List<String> actual = reader.readFromFile(TEST_FILE);
        assertEquals(expected, actual);
    }
}
