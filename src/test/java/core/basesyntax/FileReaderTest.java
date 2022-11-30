package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.services.FileReaderImpl;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderTest {
    private static FileReaderImpl fileReader;
    private static final String FRUIT_TRANSACTION_FILE_NAME = "src/main/resources/transactions.csv";

    @BeforeClass
    public static void beforeClass() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void readFromFile_CorrectPath_Ok() {
        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        List<String> actual = fileReader.readFromFile(FRUIT_TRANSACTION_FILE_NAME);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_IncorrectPath_NotOk() {
        String filePath = "incorrectPath";
        fileReader.readFromFile(filePath);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_NullPath_NotOk() {
        String nullFilePath = null;
        fileReader.readFromFile(nullFilePath);
    }
}
