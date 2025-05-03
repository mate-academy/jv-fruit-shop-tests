package core.basesyntax.dao;

import core.basesyntax.exceptions.NullFileNameException;
import java.io.File;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataReaderTest {
    private static DataReader dataReader;

    @BeforeClass
    public static void beforeClass() {
        dataReader = new FruitFileReader();
    }

    @Test(expected = NullFileNameException.class)
    public void read_NullFileName_NotOk() {
        dataReader.readData(null);
    }

    @Test(expected = RuntimeException.class)
    public void read_UnfoundedFile_NotOk() {
        dataReader.readData("src\\main\\resources\\tasty.csv");
    }

    @Test
    public void read_BasicFile_Ok() {
        String[] pathToFileElements = {"src", "main", "resources", "test.csv"};
        String pathToFile = String.join(File.separator, pathToFileElements);
        List<String> transactionList = dataReader.readData(pathToFile);
        int expectedTransactionListSize = 8;
        Assert.assertEquals(expectedTransactionListSize, transactionList.size());
    }
}
