package core.basesyntax.service.impl;

import core.basesyntax.service.Reader;
import core.basesyntax.service.impl.ReadFromFileImpl;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ReaderFromFileImplTest {
    private static final String INPUT_FILE_PATH = "src/test/resources/test_resourse.csv";
    private Reader reader;

    @Before
    public void setUp() throws Exception {
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
    public void readFromFileFile_NotOk() {
        List<String> expected = new ArrayList<>();
        expected.add("first line");
        expected.add("another string");
        List<String> actual = reader.readFromFile(INPUT_FILE_PATH);
        assertFalse(expected.equals(actual));
    }

    @Test(expected = RuntimeException.class)
    public void readFile_NotOk() {
        reader.readFromFile(null);
    }

    @Test(expected = RuntimeException.class)
    public void read_non_existent_path_NotOk() {
        reader.readFromFile("");
    }
}
