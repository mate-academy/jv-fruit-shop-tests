package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String FILE_WITH_ONE_LINE_NAME
            = "src/test/resources/one_line_to_read_test.csv";
    private static final String FILE_WITH_TWO_LINE_NAME
            = "src/test/resources/two_line_to_read_test.csv";

    private static FileReaderService fileReaderService;
    private static List<String> lines;

    @BeforeClass
    public static void beforeClass() {
        lines = new ArrayList<>();
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void readFromFile_readOneLine_isValid() {
        lines.add("fruit,banana");
        List<String> actual = fileReaderService.readFromFile(FILE_WITH_ONE_LINE_NAME);
        assertEquals(lines, actual);
    }

    @Test
    public void readFromFile_readTwoLine_isValid() {
        lines.add("fruit,quantity");
        lines.add("banana,15");
        List<String> actual = fileReaderService.readFromFile(FILE_WITH_TWO_LINE_NAME);
        assertEquals(lines, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_EmptyFile_isNotValid() {
        fileReaderService.readFromFile(null);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_notValidFileName_isNotValid() {
        fileReaderService.readFromFile("/inprut.csv");
    }

    @After
    public void tearDown() {
        lines.clear();
    }
}
