package fruite.store.service.impl;

import org.junit.Assert;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String VALID_FILE_NAME_AND_PATH =
            "src/test/resources/valid-data-activities-by-day.csv";
    private static final String INVALID_FILE_NAME_OR_PATH =
            "src/tet/resouces/valid-data-activities-by-day.csv";
    private FileReaderServiceImpl fileReaderService = new FileReaderServiceImpl();

    @Test
    public void readFromFile_validData_ok() {
        String actualResult = fileReaderService.readFromFile(VALID_FILE_NAME_AND_PATH);
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
        fileReaderService.readFromFile(INVALID_FILE_NAME_OR_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_nullInsteadOfPathOrFileName_notOk() {
        fileReaderService.readFromFile(null);
    }
}
