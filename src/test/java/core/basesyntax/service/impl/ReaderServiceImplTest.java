package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String INPUT_FILE_NAME = "src/test/resources/test_data.csv";
    private static final String WRONG_FILE_NAME = "src/test/resources/data.csv";
    private static final String EMPTY_FILE_NAME = "src/test/resources/test_empty_data.csv";
    private static ReaderService readerService;

    @BeforeClass
    public static void beforeClass() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readFromFile_ok() {
        List<String> expected = new ArrayList<>();
        expected.add("test file should contains");
        expected.add("at least two lines");
        List<String> actual = readerService.readFromFile(INPUT_FILE_NAME);
        assertEquals(expected, actual);
    }

    @Test
    public void readFromFile_emptyFile_ok() {
        List<String> expected = new ArrayList<>();
        List<String> actual = readerService.readFromFile(EMPTY_FILE_NAME);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_wrongFileName_notOk() {
        readerService.readFromFile(WRONG_FILE_NAME);
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_emptyFileName_notOk() {
        readerService.readFromFile("");
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_nullFileName_notOk() {
        readerService.readFromFile(null);
    }
}
