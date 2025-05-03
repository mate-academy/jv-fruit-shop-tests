package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String TITLE_ROW = "type,fruit,quantity";
    private static final File EMPTY_FILE = new File("src/test/resources/input/empty.csv");
    private static final String WRONG_PATH = "src/wrong/path";
    private static final String NULL_PATH = null;
    private static ReaderService testReader;
    private static List<String> emptyList;
    private static List<String> validList;

    @BeforeClass
    public static void beforeClass() {
        testReader = new ReaderServiceImpl();
        emptyList = new ArrayList<>();
        validList = new ArrayList<>();
    }

    @AfterClass
    public static void afterClass() {
        validList.clear();
    }

    @Test
    public void read_emptyFile_ok() {
        List<String> actual = testReader.read(EMPTY_FILE);
        List<String> expected = emptyList;
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void read_wrongPath_notOk() {
        testReader.read(new File(WRONG_PATH));
    }

    @Test(expected = NullPointerException.class)
    public void read_nullPath_notOk() {
        testReader.read(new File(NULL_PATH));
    }

    @Test
    public void read_validFile_ok() {
        validList.add(TITLE_ROW);
        validList.add("b,banana,20");
        validList.add("b,apple,100");
        validList.add("s,banana,100");
        validList.add("p,banana,13");
        validList.add("r,apple,10");
        validList.add("r,apple,0");
        validList.add("p,apple,20");
        validList.add("p,banana,5");
        validList.add("s,banana,50");
        List<String> actual = testReader.read(new File("src/test/resources/input/valid.csv"));
        List<String> expected = validList;
        assertEquals(expected, actual);
    }
}
