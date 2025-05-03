package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static ReaderService readerService;
    private static final String VALID_FILE_NAME = "src/test/resources/dataInputTest.csv";
    private static final String INVALID_FILE_NAME = "///invalid\\\\file///name\\cssv";
    private List<String> listString;

    @BeforeClass
    public static void beforeClass() throws Exception {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void reader_validFileName_Ok() {
        listString = new ArrayList<>();
        listString.add("type,fruit,quantity");
        listString.add("b,banana,20");
        listString.add("b,apple,100");
        List<String> actual = readerService.readFromFile(VALID_FILE_NAME);
        assertEquals(listString, actual);
    }

    @Test (expected = RuntimeException.class)
    public void reader_invalidFileName_notOk() {
        readerService.readFromFile(INVALID_FILE_NAME);
    }

    @Test (expected = RuntimeException.class)
    public void reader_nullFileName_notOk() {
        readerService.readFromFile(null);
    }
}
