package core.basesyntax.service.file;

import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileReaderTest {
    private static final String VALID_PATH_TO_FILE = "src/test/resources/validDailyActivities.csv";
    private static final String INVALID_PATH_TO_FILE =
            "src/test777/resources/invalidDailyActivities.csv";
    private static CsvFileReader csvFileReader;

    @BeforeClass
    public static void beforeClass() {
        csvFileReader = new CsvFileReader();
    }

    @Test
    public void readeFile_validFile_Ok() {
        List<String> expectedValue =
                List.of("type,fruit,quantity",
                        "b,banana,20",
                        "b,apple,100",
                        "s,banana,100");
        List<String> actualValue = csvFileReader.readFile(VALID_PATH_TO_FILE);
        Assert.assertEquals(actualValue, expectedValue);
    }

    @Test(expected = RuntimeException.class)
    public void fileReader_invalidPath_NotOk() {
        List<String> actualValue = csvFileReader.readFile(INVALID_PATH_TO_FILE);
    }
}
