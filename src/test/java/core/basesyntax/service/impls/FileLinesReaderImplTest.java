package core.basesyntax.service.impls;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileLinesReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FileLinesReaderImplTest {
    private static final String TEST_INPUT_FILE = "src/test/resources/test_input.csv";
    private static final String EMPTY_FILE = "src/test/resources/empty.csv";
    private final FileLinesReader testReader = new FileLinesReaderImpl();
    private List<String> expected;

    @Before
    public void setUp() {
        expected = new ArrayList<>();
    }

    @Test
    public void reading_existingFile_isOk() {
        expected.add("b,banana,20");
        assertEquals(expected, testReader.dataFromFile(TEST_INPUT_FILE));
    }

    @Test
    public void reading_emptyFile_isOk() {
        assertEquals(expected, testReader.dataFromFile(EMPTY_FILE));
    }

    @Test (expected = RuntimeException.class)
    public void reading_missingFile_throwsException() {
        testReader.dataFromFile("wrong_path");
    }
}
