package core.basesyntax.fileservise;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String CORRECT_FILE_PATH = "src\\test\\resourcesTest\\input_Date.csv";
    private static final String INCORRECT_FILE_PATH = "src\\main\\shopExample.cvs";
    private static final String PATH_EMPTY_FILE = "src\\test\\resourcesTest\\emptyFile.csv";
    private static ReaderService readerService;
    private static List<String> actual;
    private static List<String> expected;

    @Before
    public void setUp() throws Exception {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void rear_dataFromFile_Ok() {
        actual = readerService.read(CORRECT_FILE_PATH);
        expected = List.of("type,fruit,quantity",
                "b,banana,40",
                "b,apple,100", 
                "s,banana,100", 
                "p,banana,13",
                "r,apple,10", 
                "p,apple,20", 
                "p,banana,5", 
                "s,banana,50");
        assertEquals(expected, actual);
    }

    @Test
    public void read_emptyFile_Ok() {
        actual = readerService.read(PATH_EMPTY_FILE);
        expected = new ArrayList<>();
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void read_fromIncorrectPath_Ok() {
        readerService.read(INCORRECT_FILE_PATH);
    }
}
