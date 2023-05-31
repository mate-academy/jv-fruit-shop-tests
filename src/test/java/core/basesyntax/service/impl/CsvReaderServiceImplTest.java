package core.basesyntax.service.impl;

import core.basesyntax.service.FileReaderService;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvReaderServiceImplTest {
    private static final String OK_FILE_NAME = "src/test/resources/actualInput.csv";
    private static List<String> readCorrectData;
    private static FileReaderService readerService;

    @BeforeClass
    public static void beforeClass() {
        readerService = new CsvReaderServiceImpl();
        readCorrectData = List.of("type,fruit,quantity",
                                     "b,banana,20",
                                     "b,apple,100",
                                     "s,banana,100",
                                     "p,banana,13",
                                     "r,apple,10",
                                     "p,apple,20",
                                     "p,banana,5",
                                     "s,banana,50");
    }

    @Test(expected = NullPointerException.class)
    public void read_fileNameNull_notOk() {
        readerService.readFromFile(null);
    }

    @Test(expected = RuntimeException.class)
    public void read_badFilePath_notOk() {
        readerService.readFromFile("");
    }

    @Test
    public void read_correctData_ok() {
        List<String> actual = readerService.readFromFile(OK_FILE_NAME);
        Assert.assertEquals(readCorrectData, actual);
    }
}
