package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.impl.FileReaderServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FileReaderServiceTest {
    private static final String EMPTY_FILE_TEST = "src/test/resources/testInputEmptyFile";
    private static final String FILE_TEST = "src/test/resources/testInputFile";
    private FileReaderService fileReaderServiceTest = new FileReaderServiceImpl();
    private List<String> expected;

    @Before
    public void setUp() {
        expected = new ArrayList<>();
        expected.clear();
    }

    @Test
    public void readEmptyFile_notOk() {
        List<String> actual = fileReaderServiceTest.read(EMPTY_FILE_TEST);
        assertTrue(actual.isEmpty());
    }

    @Test
    public void readFile_Ok() {
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        List<String> actual = fileReaderServiceTest.read(FILE_TEST);
        assertEquals(expected, actual);
    }
}
