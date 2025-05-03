package core.basesyntax.services.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.services.interfaces.FileReader;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String PATH_TO_FILE = "src/test/java/core/basesyntax/sources/"
            + "test_report.csv";
    private static final String WRONG_PATH_TO_FILE = "src/test/java/core/basesyntax/sources/"
            + "fail_test_report.csv";
    private static final int TITLE_INDEX = 0;
    private static final int FIRST_LINE_INDEX = 1;
    private static FileReader fileReader;
    private List<String> linesFromFile;

    @BeforeClass
    public static void beforeClass() {
        fileReader = new FileReaderImpl();
    }

    @Before
    public void setUp() {
        linesFromFile = fileReader.getLinesFromFile(PATH_TO_FILE);
    }

    @Test
    public void readTitlesLine_Ok() {
        String actual = linesFromFile.get(TITLE_INDEX);
        String expected = "type,fruit,quantity";
        assertEquals(expected, actual);
    }

    @Test
    public void readSecondLine_Ok() {
        String actual = linesFromFile.get(FIRST_LINE_INDEX);
        String expected = "b,banana,20";
        assertEquals(expected, actual);
    }

    @Test
    public void countLinesInFile_Ok() {
        int actual = linesFromFile.size();
        assertEquals(3, actual);
    }

    @Test
    public void getEmptyListWhenFileNotExists_Ok() {
        linesFromFile = fileReader.getLinesFromFile(WRONG_PATH_TO_FILE);
        int actual = linesFromFile.size();
        int expected = 0;
        assertEquals(expected, actual);
    }
}
