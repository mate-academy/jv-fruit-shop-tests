package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String TEST_FILE_HEADER
            = "src/test/resources/test_file_header.csv";
    private static ReaderService readerService;
    private static List<String> lines;

    @BeforeClass
    public static void beforeClass() {
        readerService = new ReaderServiceImpl();
        lines = new ArrayList<>();
    }

    @Test
    public void readFromFile_readHeader_isOk() {
        lines.add("fruit,quantity");
        List<String> actual = readerService.readFromFile(TEST_FILE_HEADER);
        assertEquals(lines, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_notValidFileName_isNotOk() {
        readerService.readFromFile("src/test/resources/testfileheader.csv");
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_EmptyFile_isNotOk() {
        readerService.readFromFile(null);
    }

    @After
    public void tearDown() {
        lines.clear();
    }
}
