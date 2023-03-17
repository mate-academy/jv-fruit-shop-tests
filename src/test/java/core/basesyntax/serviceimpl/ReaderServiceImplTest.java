package core.basesyntax.serviceimpl;

import org.junit.Test;
import core.basesyntax.service.ReaderService;
import core.basesyntax.serviceimpl.ReaderServiceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ReaderServiceImplTest {
    private static final File EMPTY_FILE = new File("src/test/java/testresources/empty.csv");
    private static final String WRONG_PATH = "src/wrong/path";
    private static final String NULL_PATH = null;
    private final ReaderService testReader = new ReaderServiceImpl();
    private final List<String> validList = new ArrayList<>();

    @Test(expected = RuntimeException.class)
    public void reading_EmptyFile_notOk() {
        List<String> actual = testReader.read(EMPTY_FILE);
        List<String> expected = new ArrayList<>();
        assertEquals(actual, expected);
    }

    @Test(expected = RuntimeException.class)
    public void reading_WrongPath_notOk() {
        List<String> actual = testReader.read(new File(WRONG_PATH));
    }

    // no sense, because NPE throws here (new File(NULL_PATH)
    @Test(expected = NullPointerException.class)
    public void reading_NullPath_notOk() {
        List<String> actual = testReader.read(new File(NULL_PATH));
    }

    @Test
    public void reading_ValidFile_ok() {
        validList.add("type,fruit,quantity");
        validList.add("b,banana,20");
        validList.add("b,apple,100");
        validList.add("s,banana,100");
        validList.add("p,banana,13");
        validList.add("r,apple,10");
        validList.add("r,apple,0");
        validList.add("p,apple,20");
        validList.add("p,banana,5");
        validList.add("s,banana,50");
        List<String> actual = testReader.read(new File("src/test/java/testresources/input/valid.csv"));
        assertEquals(actual, validList);
    }
}
