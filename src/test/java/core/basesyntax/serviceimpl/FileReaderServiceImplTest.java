package core.basesyntax.serviceimpl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FileReaderServiceImplTest {
    public static final String PATH_TO_EMPTY_FILE = "src/main/resources/Empty_file.csv";
    public static final String PATH_TO_INPUT_FILE = "src/main/resources/Input_file.csv";

    private ReaderService readerService;

    @Before
    public void setUp() {

        readerService = new FileReaderServiceImpl();
    }

    @Test
    public void readFromFile_Ok() {
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20",
                "b,apple,100", "s,banana,100", "p,banana,13", "r,apple,10",
                "p,apple,20", "p,banana,5", "s,banana,50");
        List<String> actual = readerService.readFromFile(PATH_TO_INPUT_FILE);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_NotOk() {
        readerService.readFromFile(PATH_TO_EMPTY_FILE);
    }
}
