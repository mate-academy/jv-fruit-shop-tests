package core.basesyntax.dao;

import core.basesyntax.exceptions.InvalidLineException;
import core.basesyntax.exceptions.NullFileNameException;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class DataReaderTest {
    private DataReader dataReader;

    @Test(expected = NullFileNameException.class)
    public void read_NullFileName_NotOk() {
        dataReader = new FruitFileReader(null);
    }

    @Test(expected = RuntimeException.class)
    public void read_UnfoundedFile_NotOk() {
        dataReader = new FruitFileReader("src\\main\\resources\\tasty.csv");
        dataReader.readDataLines();
    }

    @Test(expected = InvalidLineException.class)
    public void read_FileWithEmptyLine_NotOk() {
        dataReader = new FruitFileReader(
                ".");
        dataReader.readDataLines();
    }

    @Test(expected = InvalidLineException.class)
    public void read_FileWithWrongTransactionElements_NotOk() {
        dataReader = new FruitFileReader(
                ".");
        dataReader.readDataLines();
    }

    @Test
    public void read_BasicFile_Ok() {
        dataReader = new FruitFileReader(".");
        List<String> transactionList = dataReader.readDataLines();
        int expectedTransactionListSize = 8;
        Assert.assertEquals(expectedTransactionListSize, transactionList.size());
    }
}
