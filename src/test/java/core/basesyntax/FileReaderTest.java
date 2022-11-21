package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.services.FileReaderImpl;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderTest {
    private static FileReaderImpl fileReader;

    @BeforeClass
    public static void beforeClass() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void fileReader_CorrectPath_Ok() {
        String filePath = "src/main/resources/transactions.csv";
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
        List<String> actual = fileReader.readFromFile(filePath);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void fileReader_IncorrectPath_NotOk() {
        String filePath = "incorrectPath";
        fileReader.readFromFile(filePath);
    }

    @Test(expected = RuntimeException.class)
    public void fileReader_NullPath_NotOk() {
        String filePath = null;
        fileReader.readFromFile(filePath);
    }
}
