package service.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import service.FileReaderService;

public class CsvFileReaderServiceTest {
    private static FileReaderService fileReadService;
    private static final String[][] STENCIL = {{"banana", "250"}, {"apple", "190"}};
    private static final String FILE_PATH_FROM = "src/test/resources/readingTest.csv";

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void initial() {
        fileReadService = new CsvFileReaderServiceImpl();
    }

    @Test
    public void readFile_readCsvFile_ok() {
        List<String[]> list = fileReadService.readFile(FILE_PATH_FROM);
        assertArrayEquals("strings from first row of the csv file and obtained"
                + " result aren't the same", STENCIL[0], list.get(0));
        assertArrayEquals("strings from second row of the csv file and obtained"
                + " result aren't the same", STENCIL[1], list.get(1));
        assertEquals("expected row's quantity and quantity of rows from"
                + " stencil aren't the same", STENCIL.length, list.size());
    }

    @Test
    public void readFile_pathIsNull_notOk() {
        exception.expect(NullPointerException.class);
        fileReadService.readFile(null);
    }

    @Test
    public void readFile_filePathNotCorrect_notOk() {
        String filePathError = "mistaken/path/file.csv";
        exception.expect(RuntimeException.class);
        exception.expectMessage("Cannot read file");
        fileReadService.readFile(filePathError);
    }
}
