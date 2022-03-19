package core.basesyntax.dao;

import core.basesyntax.exceptions.NullFileNameException;
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
}
