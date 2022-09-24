package core.basesyntax.service.writereadcsv;

import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class FileReaderImpTest {
    private static final String FILE_TO_READ =
            "src/test/java/core/basesyntax/resources/input/fruit.csv";
    private FileReader fileReader;
    private List<String> expected;

    public FileReaderImpTest() {
        fileReader = new FileReaderImp();
    }

    @Test
    public void readFromFileCsv_Ok() {
        List<String> actual = fileReader.readFromFileCsv(FILE_TO_READ);
        Assert.assertEquals(expected.toString(),actual.toString());
    }

    @Test
    public void readNullFileName_notOk() {
        try {
            fileReader.readFromFileCsv(null);
        } catch (NullPointerException e) {
            return;
        }
        fail("You need throw NullPointerException");
    }

    @Test
    public void readNotValidFileName_notOk() {
        try {
            String notValidFileName = "SuperFruit";
            fileReader.readFromFileCsv(notValidFileName);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException: Can't read data from file, not valid Path or file format");
    }

    @Test
    public void readFromNotValidFormatFile() {
        String notValidFormatFile =
                "src/test/java/core/basesyntax/resources/input/fruit.fruit";
        try {
            fileReader.readFromFileCsv(notValidFormatFile);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException: Can't read data from file, not valid Path or file format");
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
