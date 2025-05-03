package core.basesyntax;

import core.basesyntax.db.Storage;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class FileReaderTest {
    private static final String PATH_REPORT_TO_READ
            = "src/test/java/resources/reportToRead.csv";

    @Test
    public void testReportToRead_Ok() {
        Storage.FruitTransactionStorage.clear();
        FileReaderCsv fileReaderCsv = new FileReaderCsvImpl();
        List<String> expected = List.of("type,product,quantity",
                "b,banana,20", "b,apple,100", "s,banana,100", "p,banana,13",
                "r,apple,10", "p,apple,20", "p,banana,5", "s,banana,50");
        Assert.assertEquals(expected, fileReaderCsv.readFile(PATH_REPORT_TO_READ));
    }

    @Test
    public void testReportToReadPath_NotOk() {
        FileReaderCsv fileReaderCsv = new FileReaderCsvImpl();
        Assert.assertThrows(RuntimeException.class,
                () -> fileReaderCsv.readFile("path/reportToRead.csv"));
    }

}
