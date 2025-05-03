package core.basesyntax.service.writereadcsv;

import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FileReaderImpTest {
    private static final String FILE_TO_READ =
            "src/test/java/core/basesyntax/resources/input/fruit.csv";
    private FileReader fileReader;
    private List<String> expected;

    @Before
    public void setUp() throws Exception {
        fileReader = new FileReaderImp();
    }

    @Test
    public void readFromFileCsv_ValidDate_Ok() {
        List<String> actual = fileReader.readFromFileCsv(FILE_TO_READ);
        Assert.assertEquals(expected,actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFileCsv_NullFileName_notOk() {
        fileReader.readFromFileCsv(null);
    }

    @Test (expected = RuntimeException.class)
    public void readFromFileCsv_NotValidFileName_notOk() {
        String notValidFileName = "SuperFruit";
        fileReader.readFromFileCsv(notValidFileName);
    }

    @Test (expected = RuntimeException.class)
    public void readFromFileCsv_NotValidFormatFile_NotOk() {
        String notValidFormatFile =
                "src/test/java/core/basesyntax/resources/input/fruit.fruit";
        fileReader.readFromFileCsv(notValidFormatFile);
    }

    {
        expected = Arrays.asList("type,fruitTransaction,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
    }
}
