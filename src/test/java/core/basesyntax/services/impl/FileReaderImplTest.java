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
    private static final String EXPECTED_TITLE = "type,fruit,quantity";
    private static final String EXPECTED_SECOND_LINE = "b,banana,20";
    private static final int EXPECTED_LINES_COUNT = 3;
    private static final int EXPECTED_LIST_SIZE_WHEN_FILE_NOT_EXISTS = 0;
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
        assertEquals(EXPECTED_TITLE, actual);
    }

    @Test
    public void readSecondLine_Ok() {
        String actual = linesFromFile.get(FIRST_LINE_INDEX);
        assertEquals(EXPECTED_SECOND_LINE, actual);
    }

    @Test
    public void countLinesInFile_Ok() {
        int actual = linesFromFile.size();
        assertEquals(EXPECTED_LINES_COUNT, actual);
    }

    @Test
    public void getEmptyListWhenFileNotExists_Ok() {
        linesFromFile = fileReader.getLinesFromFile(WRONG_PATH_TO_FILE);
        int actual = linesFromFile.size();
        assertEquals(EXPECTED_LIST_SIZE_WHEN_FILE_NOT_EXISTS, actual);
    }
}
