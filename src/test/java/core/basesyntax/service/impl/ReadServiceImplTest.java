package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReadService;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ReadServiceImplTest {
    public static final String INPUT_FILE_PATH = "src/test/java/resources/fruits_info.csv";
    public static final String INPUT_FILE_PATH_IS_EMPTY = "src/test/java/resources/empty_file.csv";
    private ReadService readService;

    @Before
    public void setUp() {
        readService = new ReadServiceImpl();
    }

    @Test
    public void readService_ExistedFile_Ok() {
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20", "b,apple,100",
                "s,banana,100", "p,banana,13", "r,apple,10", "p,apple,20",
                "p,banana,5", "s,banana,50");
        List<String> actual = readService.readFromFile(INPUT_FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test
    public void readService_EmptyFile_Ok() {
        List<String> expected = List.of();
        List<String> actual = readService.readFromFile(INPUT_FILE_PATH_IS_EMPTY);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void readService_NotExistedFile_NotOk() {
        readService.readFromFile("");
    }
}
