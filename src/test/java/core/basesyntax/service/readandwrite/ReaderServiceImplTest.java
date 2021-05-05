package core.basesyntax.service.readandwrite;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String PATH_TO_FILE = "src/test/resources/validFile.csv";
    private static final String WRONG_PATH = "src/test/resources/v.csv";
    private static ReaderService readerService;
    private static List<String> expected;

    @BeforeClass
    public static void beforeClass() {
        readerService = new ReaderServiceImpl();
        expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("    b,banana,20");
        expected.add("    b,apple,100");
    }

    @Test
    public void readFile_Ok() {
        List<String> actual = readerService.readFile(PATH_TO_FILE);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void readFile_NotOk() {
        readerService.readFile(WRONG_PATH);
    }
}
