package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exception.FruitShopException;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileReaderServiceImplTest {
    private static final String CORRECT_FILE_DIRECTORY =
            "src/test/java/core/basesyntax/sourceFile/correctFileReaderTest.csv";
    private static final String NON_EXISTING_FILE_DIRECTORY =
            "src/test/java/core/basesyntax/sourceFile/nonExist.csv";
    private static CsvFileReaderServiceImpl fileReader;
    private static List<String> expected;

    @BeforeClass
    public static void beforeClass() {
        fileReader = new CsvFileReaderServiceImpl();
        expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        );
    }

    @Test(expected = FruitShopException.class)
    public void readFromFile_readNonExistingFile_notOk() {
        fileReader.readFromFile(NON_EXISTING_FILE_DIRECTORY);
    }

    @Test
    public void readFromFile_readCorrectFile_ok() {
        List<String> actual = fileReader.readFromFile(CORRECT_FILE_DIRECTORY);
        assertEquals(expected, actual);
    }

    @Test(expected = FruitShopException.class)
    public void readFromFile_readNullFile_notOk() {
        fileReader.readFromFile(null);
    }

}
