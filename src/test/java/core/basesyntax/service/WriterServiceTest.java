package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class WriterServiceTest {
    private static final String DEFAULT_INPUT = "abcd";
    private static final String PATH_TO_FILE =
            "D:\\Progr\\jv-fruit-shop-tests\\src\\test\\resources\\toWrite.csv";
    private static final String EMPTY_DATA = "";
    private static ReaderService readerService;
    private static WriterService writerService;

    @Before
    public void setUp() {
        readerService = new ReaderServiceImpl();
        writerService = new WriterServiceImpl();
    }

    @After
    public void tearDown() {
        try {
            Files.deleteIfExists(Path.of(PATH_TO_FILE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testWrite_correctCase_ok() {
        writerService.write(DEFAULT_INPUT, PATH_TO_FILE);
        assertEquals(readerService.readFrom(PATH_TO_FILE), DEFAULT_INPUT);
    }

    @Test
    public void testWrite_emptyData_ok() {
        writerService.write(EMPTY_DATA, PATH_TO_FILE);
        assertEquals(readerService.readFrom(PATH_TO_FILE), EMPTY_DATA);
    }

    @Test(expected = RuntimeException.class)
    public void testWrite_nullData_notOk() {
        writerService.write(null, null);
    }

    @Test(expected = RuntimeException.class)
    public void testWrite_firstArgumentNull_notOk() {
        writerService.write(null, PATH_TO_FILE);
    }

    @Test(expected = RuntimeException.class)
    public void testWrite_secondArgumentNull_notOk() {
        writerService.write(DEFAULT_INPUT, null);
    }
}
