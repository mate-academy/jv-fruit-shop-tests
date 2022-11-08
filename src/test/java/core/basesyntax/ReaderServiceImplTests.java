package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTests {
    private static final String NON_EXISTENT_FILE = "src/main/resources/noFile.csv";
    private static final String EMPTY_FILE_PATH = "src/main/resources/empty.csv";
    private static final String NORMAL_FILE_PATH = "src/main/resources/input.csv";
    private static ReaderService dataReader;
    private static List<String> expected;

    @BeforeClass
    public static void beforeClass() {
        dataReader = new ReaderServiceImpl();
        expected = new ArrayList<>();
    }

    @Test(expected = RuntimeException.class)
    public void read_nonExistentFile_notOk() {
        dataReader.read(NON_EXISTENT_FILE);
    }

    @Test
    public void read_emptyFile_ok() {
        List<String> actual = dataReader.read(EMPTY_FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test
    public void read_normalFile_ok() {
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50");
        List<String> actual = dataReader.read(NORMAL_FILE_PATH);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        expected.clear();
    }
}
