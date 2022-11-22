package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String TEST_PATH_INPUT = "src/test/resources/input.csv";
    private static final String WRONG_TEST_PATH_INPUT = "wrong_path";
    private static final String ETALON_STRING = "type,fruit,quantity";
    private static FileReaderImpl fileReader;

    @BeforeClass
    public static void init() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void read_correctFile_ok() {
        assertEquals("Expected another string",
                ETALON_STRING, fileReader.read(TEST_PATH_INPUT).get(0));
    }

    @Test (expected = RuntimeException.class)
    public void read_nonExistFile_NotOK() {
        fileReader.read(WRONG_TEST_PATH_INPUT);
    }
}
