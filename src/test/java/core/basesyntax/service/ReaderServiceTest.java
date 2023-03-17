package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.impl.ReaderServiceImpl;
import org.junit.Before;
import org.junit.Test;

public class ReaderServiceTest {
    private static final String DEFAULT_FILE_PATH =
            "D:\\Progr\\jv-fruit-shop-tests\\src\\test\\resources\\reader.csv";
    private static final String CORRECT_DATA = "abcd";
    private static final String EMPTY_DATA_PATH =
            "D:\\Progr\\jv-fruit-shop-tests\\src\\test\\resources\\empty.csv";
    private static final String INCORRECT_PATH =
            "D:\\Progr\\jv-fruit-shop-tests\\src\\test\\resources";
    private static final String EMPTY_DATA = "";
    private static ReaderService readerService;

    @Before
    public void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void testReadFromFile_correctOutput() {
        assertEquals(CORRECT_DATA, readerService.readFrom(DEFAULT_FILE_PATH));
    }

    @Test
    public void testReadFromFile_emptyData() {
        assertEquals(EMPTY_DATA, readerService.readFrom(EMPTY_DATA_PATH));
    }

    @Test(expected = RuntimeException.class)
    public void testReadFromFile_nullPath() {
        readerService.readFrom(null);
        fail("Expected an exception to be thrown");
    }

    @Test(expected = RuntimeException.class)
    public void testReadFromFile_incorrectPath() {
        readerService.readFrom(INCORRECT_PATH);
        fail("Expected an exception to be thrown");
    }
}
