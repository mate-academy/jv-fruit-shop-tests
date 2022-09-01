package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReaderService;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileReaderServiceTest {
    private static final String FOLDER = "src/test/resources";
    private static FileReaderService csvFileReaderService;

    @BeforeClass
    public static void setUp() {
        csvFileReaderService = new CsvFileReaderService();
    }

    @Test(expected = RuntimeException.class)
    public void read_nullFile_notOk() {
        csvFileReaderService.read(null);
    }

    @Test(expected = RuntimeException.class)
    public void read_noFile_notOk() {
        File noFile = new File(FOLDER, "nameNotExistingFile.txt");
        csvFileReaderService.read(noFile);
    }

    @Test(expected = RuntimeException.class)
    public void read_notValidFileName_notOk() {
        File noFile = new File(FOLDER, "ad*-dffgb_/ sdgf0( \\.txt");
        csvFileReaderService.read(noFile);
    }

    @Test
    public void read_File_ok() {
        File file = new File(FOLDER, "readerTestInput.csv");
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
        assertEquals("List strings is not equals", expected, actual);
    }
}
