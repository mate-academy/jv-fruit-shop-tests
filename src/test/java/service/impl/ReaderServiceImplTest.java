package service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String TWO_FRUITS_FILE
            = "src/test/resources/IncomingReports/TwoFruits.csv";
    private static final String EMPTY_FILE
            = "src/test/resources/IncomingReports/EmptyInput.csv";
    private static final String INVALID_FILE
            = "&812375ba^_^";
    private static final String INVALID_PATH_FILE
            = " //^_^\\ ";
    private static List<String> expectedStrings;

    @BeforeClass
    public static void set() {
        expectedStrings = new ArrayList<>();
        expectedStrings.add("type,fruit,quantity");
        expectedStrings.add("b,banana,20");
        expectedStrings.add("b,apple,100");
        expectedStrings.add("s,banana,100");
        expectedStrings.add("p,banana,13");
        expectedStrings.add("r,apple,10");
        expectedStrings.add("p,apple,20");
        expectedStrings.add("p,banana,5");
        expectedStrings.add("s,banana,50");
    }

    @Test(expected = RuntimeException.class)
    public void readFile_invalidPath_notOk() {
        new ReaderServiceImpl().readFile(INVALID_PATH_FILE);
    }

    @Test(expected = RuntimeException.class)
    public void readFile_invalidFile_notOk() {
        new ReaderServiceImpl().readFile(INVALID_FILE);
    }

    @Test(expected = RuntimeException.class)
    public void readFile_null_notOk() {
        new ReaderServiceImpl().readFile(null);
    }

    @Test
    public void readFile_emptyFile_ok() {
        List<String> actual = new ReaderServiceImpl().readFile(EMPTY_FILE);
        assertTrue("Reading empty file should return empty list", actual.isEmpty());
    }

    @Test
    public void readFile_ok() {
        List<String> actualStrings = new ReaderServiceImpl().readFile(TWO_FRUITS_FILE);
        for (int i = 0; i < actualStrings.size(); i++) {
            assertEquals("Reading file error on line " + i,
                    expectedStrings.get(i), actualStrings.get(i));
        }
    }
}
