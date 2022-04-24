package basesyntax.service.read;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceTest {
    private static ReaderService readerService;

    @BeforeClass
    public static void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_EmptyFile_NotOk() {
        readerService.readFile("empty.csv");
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_EmptyPath_NotOk() {
        readerService.readFile("");
    }

    @Test (expected = NullPointerException.class)
    public void readFromFile_NullFilePath_NotOk() {
        readerService.readFile(null);
    }

    @Test
    public void readFromFile_ValidCase_Ok() {
        List<String> expected = List.of("type,fruit,quantity",
                "b,banana,18",
                "b,apple,100",
                "s,banana,14",
                "p,banana,15",
                "r,apple,100000",
                "p,apple,50",
                "p,banana,5",
                "s,banana,544");
        List<String> actual = readerService.readFile("valid.csv");
        assertEquals(expected, actual);
    }
}
