package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.reader.ReaderService;
import core.basesyntax.service.reader.ReaderServiceImpl;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceTest {
    private static final String ILLEGAL_FILE_PATH = "###/###.csv";
    private static final String VALID_FILE_PATH = "src/test/resources/inputDataTest.csv";
    private static final List<String> expected =
            List.of("type,fruit,quantity", "b,banana,20", "b,apple,100");
    private static ReaderService readerService;

    @BeforeClass
    public static void beforeAll() {
        readerService = new ReaderServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void readeFromFile_illegalFilePath_RuntimeException() {
        readerService.readFromFile(ILLEGAL_FILE_PATH);
    }

    @Test
    public void writeReportToFile_validFilePath_returnsTrue() {
        List<String> actual = readerService.readFromFile(VALID_FILE_PATH);
        assertEquals(expected, actual);
    }
}
