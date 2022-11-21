package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String TEST_PATH_INPUT = "src/test/resources/input.csv";
    private static final String WRONG_TEST_PATH_INPUT = "src/test/resources/putlerKAPUT.csv";
    private static final String ETALON_STRING = "type,fruit,quantity";
    private static final int QUANTITY_STRING = 1;
    private static FileReaderImpl fileReader;
    private static List<String> operations;

    @BeforeClass
    public static void init() {
        fileReader = new FileReaderImpl();
        operations = new ArrayList<>();
    }

    @Test
    public void checkReadingFile_Ok() {
        operations = fileReader.read(TEST_PATH_INPUT);
        assertTrue("Expected another string", operations.get(0).equals(ETALON_STRING));
    }

    @Test
    public void checkGotQuantityStringsFRomFile_Ok() {
        operations = fileReader.read(TEST_PATH_INPUT);
        assertEquals("Wrong quantity of string ", QUANTITY_STRING, operations.size());
    }

    @Test (expected = RuntimeException.class)
    public void checkReadingFile_NotOK() {
        operations = fileReader.read(WRONG_TEST_PATH_INPUT);
    }

    @After
    public void clearList() {
        operations.clear();
    }
}
