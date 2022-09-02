package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import core.basesyntax.service.Reader;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ReaderFromFileImplTest {
    private static final String INPUT_FILE_PATH = "src/test/resources/test_readFromFile.csv";
    private Reader reader;

    @Before
    public void setUp() {
        reader = new ReadFromFileImpl();
    }

    @Test
    public void readFile_Ok() {
        List<String> expected = new ArrayList<>();
        expected.add("first line");
        expected.add("second line");
        List<String> actual = reader.readFromFile(INPUT_FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test
    public void readFromFile_NotOk() {
        List<String> expected = new ArrayList<>();
        expected.add("first line");
        expected.add("another string");
        List<String> actual = reader.readFromFile(INPUT_FILE_PATH);
        assertFalse(expected.equals(actual));
    }

    @Test(expected = RuntimeException.class)
    public void readFile_nullInput_NotOk() {
        reader.readFromFile(null);
    }

    @Test(expected = RuntimeException.class)
    public void read_nonExistentPath_NotOk() {
        reader.readFromFile("");
    }
}
