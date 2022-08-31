package core.basesyntax.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CsvFileReaderServiceTest {
    private static final String FOLDER = "src/main/resources";
    private static final String INPUT_FILE = "input.csv";
    private static CsvFileReaderService csvFileReaderService;

    @Before
    public void setUp() {
        csvFileReaderService = new CsvFileReaderService();
    }

    @Test(expected = RuntimeException.class)
    public void read_nullFile_NotOk() {
        csvFileReaderService.read(null);
    }

    @Test(expected = RuntimeException.class)
    public void read_noFile_NotOk() {
        File noFile = new File(FOLDER, "nameNotExistingFile.txt");
        csvFileReaderService.read(noFile);
    }

    @Test(expected = RuntimeException.class)
    public void read_notValidFileName_NotOk() {
        File noFile = new File(FOLDER, "ad*-dffgb_/ sdgf0( \\.txt");
        csvFileReaderService.read(noFile);
    }

    @Test
    public void read_File_Ok() {
        File file = new File(FOLDER, INPUT_FILE);
        List<String> expected = new ArrayList<>();
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50");
        List<String> actual = csvFileReaderService.read(file);
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(actual.contains("type,fruit,quantity"),
                expected.contains("type,fruit,quantity"));
        Assert.assertEquals(actual.contains("s,banana,50"),
                expected.contains("s,banana,50"));
    }
}
