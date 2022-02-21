package fruite.store.service.impl;

import org.junit.Assert;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private FileReaderServiceImpl fileReaderService = new FileReaderServiceImpl();
    private static final String VALID_DATA_FILE =
            "src/test/resources/valid-data-activities-by-day.csv";
    private static final String INCORRECT_DATA_FILE_NAME =
            "src/tet/resouces/valid-data-activities-by-day.csv";
    private static final String FILE_WITH_NO_ACCESS =
            "src/test/resources/file-with-no-access.csv";

    @Test
    public void readFromFile_validData_ok() {
        String actualResult = fileReaderService.readFromFile(VALID_DATA_FILE);
        String expectedResult = "type,fruit,quantity" + System.lineSeparator()
                + "b,banana,20" + System.lineSeparator()
                + "b,apple,100" + System.lineSeparator()
                + "s,banana,100" + System.lineSeparator()
                + "p,banana,13" + System.lineSeparator()
                + "r,apple,10" + System.lineSeparator()
                + "p,apple,20" + System.lineSeparator()
                + "p,banana,5" + System.lineSeparator()
                + "s,banana,50" + System.lineSeparator();
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_invalidPathOrFileName_notOk() {
        fileReaderService.readFromFile(INCORRECT_DATA_FILE_NAME);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_nullInsteadOfPathOrFileName_notOk() {
        fileReaderService.readFromFile(null);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_impossibilityToOpenFile_notOk() {
        fileReaderService.readFromFile(FILE_WITH_NO_ACCESS);
    }
}