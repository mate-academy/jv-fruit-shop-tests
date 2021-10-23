package core.basesyntax.dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FruitDaocsvTest {
    private static final String TEST_INPUT_FILE = "testLog.csv";
    private static final String TEST_INPUT_CONTENT = "type,fruit,quantity" + System.lineSeparator()
            + "b,banana,20" + System.lineSeparator()
            + "b,apple,100" + System.lineSeparator()
            + "s,banana,100" + System.lineSeparator()
            + "p,banana,13" + System.lineSeparator()
            + "r,apple,10" + System.lineSeparator()
            + "p,apple,20" + System.lineSeparator()
            + "p,banana,5" + System.lineSeparator()
            + "s,banana,50";
    private static final FruitDao FRUIT_DAO = new FruitDaocsv();

    @Before
    public void setUp() {
        File testLog = new File(TEST_INPUT_FILE);
        if (!testLog.exists()) {
            try {
                testLog.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Can't create testLog", e);
            }
        }
        try {
            Files.write(testLog.toPath(), TEST_INPUT_CONTENT.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Can't write testLog", e);
        }

    }

    @After
    public void tearDown() throws Exception {
        File testLog = new File(TEST_INPUT_FILE);
        try {
            Files.deleteIfExists(testLog.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Can't remove testLog", e);
        }
    }

    @Test(expected = RuntimeException.class)
    public void nullInputFile_NotOk() {
        FRUIT_DAO.get(null);
    }

    @Test(expected = RuntimeException.class)
    public void wrongFileName_NotOk() {
        FRUIT_DAO.get("NONE.csv");
    }

    @Test
    public void testOutputData_Ok() {
        List<String> expected = Arrays.asList(TEST_INPUT_CONTENT.split(System.lineSeparator()));
        List<String> actual = FRUIT_DAO.get(TEST_INPUT_FILE);
        Assert.assertTrue(expected.containsAll(actual));
    }

}
