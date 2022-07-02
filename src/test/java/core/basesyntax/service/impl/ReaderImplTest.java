package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.Reader;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ReaderImplTest {
    private static final String EMPTY_TEST_FILE =
            "src/test/resources/emptyTestFile.csv";
    private static final String TEST_FILE =
            "src/test/resources/testFile.csv";
    private static final String INVALID_TEST_FILE =
            "src/test/resources/invalidTestFile.csv";
    private static final String NULL_DESTINATION_FILE = " ";
    private final Reader reader = new ReaderImpl();

    @Test
    public void read_emptyTestFile_ok() {
        List<String> expected = new ArrayList<>();
        List<String> actual = reader.getDataFromFile(EMPTY_TEST_FILE);
        assertEquals(expected, actual);
    }

    @Test
    public void readTestFile_ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,pineapple,10");
        expected.add("b,watermelon,87");
        expected.add("b,lemon,30");
        List<String> actual = reader.getDataFromFile(TEST_FILE);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readValidFile_notOk() {
        reader.getDataFromFile(INVALID_TEST_FILE);
    }

    @Test(expected = RuntimeException.class)
    public void readNullDestinationFile_NotOk() {
        reader.getDataFromFile(NULL_DESTINATION_FILE);
    }
}
