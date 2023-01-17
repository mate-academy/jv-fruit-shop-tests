package core.basesyntax.service;

import core.basesyntax.service.impl.ReaderOperationServiceImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderOperationServiceTest {
    private static ReaderOperationService fileReader;

    @BeforeClass
    public static void setUp() {
        fileReader = new ReaderOperationServiceImpl();
    }

    @Test(expected = NullPointerException.class)
    public void readDataFromFile_nullFilePath_notOk() {
        fileReader.readDataFromFile(null);
    }

    @Test(expected = RuntimeException.class)
    public void readDataFromFile_notValidFilePath_notOk() {
        fileReader.readDataFromFile("not_valid_file_path");
    }

    @Test
    public void readDataFromFile_validFilePath_ok() {
        String expected = "type,fruit,quantity" + System.lineSeparator()
                + "b,banana,20" + System.lineSeparator()
                + "b,apple,100" + System.lineSeparator();
        String actual = fileReader.readDataFromFile("src/test/resources/input.csv");
        Assert.assertEquals(expected, actual);
    }
}
