package core.basesyntax.serviceimpl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String TITLE_ROW = "type,fruit,quantity";
    private static final File EMPTY_FILE = new File(
            "src/test/java/core/basesyntax/resources/input/empty.csv");
    private static final String WRONG_PATH = "src/wrong/path";
    private static final String NULL_PATH = null;
    private final ReaderService testReader = new ReaderServiceImpl();
    private final List<String> emptyList = new ArrayList<>();
    private final List<String> validList = new ArrayList<>();

    @Test
    public void read_emptyFile_ok() {
        List<String> actual = testReader.read(EMPTY_FILE);
        List<String> expected = emptyList;
        assertEquals(actual, expected);
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
        List<String> actual = testReader
                .read(new File("src/test/java/core/basesyntax/resources/input/valid.csv"));
        List<String> expected = validList;
        assertEquals(actual, expected);
    }
}
