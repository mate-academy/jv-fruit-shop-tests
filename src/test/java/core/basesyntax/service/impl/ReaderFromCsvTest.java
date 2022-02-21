package core.basesyntax.service.impl;

import core.basesyntax.service.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderFromCsvTest {
    private static final int ROWS_IN_FILE = 8;
    private static final String FILE_PATH = "src/test/resources/data.csv";
    private static final String BAD_FILE_PATH = "src/test/resources/badData.csv";
    private static final String DATA = "type,fruit,quantity\n"
            + "b,banana,20\n"
            + "b,apple,100\n"
            + "s,banana,100\n"
            + "p,banana,13\n"
            + "r,apple,10\n"
            + "p,apple, 20\n"
            + "p,banana,5\n"
            + "s,banana,50";
    private static Reader reader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        reader = new ReaderFromCsv();
        Files.write(Path.of(FILE_PATH), DATA.getBytes());
    }

    @Test
    public void readFromFile_ok() {
        List<String> actual = reader.readData(FILE_PATH);
        Assert.assertEquals(ROWS_IN_FILE, actual.size());
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_fileNotExistOrBadPath_notOk() {
        reader.readData(BAD_FILE_PATH);
    }

}
