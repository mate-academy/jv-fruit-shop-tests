package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.DataReader;

import java.nio.file.Files;
import java.util.List;
import org.junit.Test;

public class DataReaderImplTest {
    private static final DataReader dataReader = new DataReaderImpl();
    private static final String TEST_FILE_NAME = "src/test/resources/InputFileTest.csv";
    private static final List<String> expected = List.of("type,fruit,quantity",
            "b,banana,20", "b,apple,100","s,banana,100", "p,banana,13");
     
    @Test
    public void readFile_Ok() {
        List<String> actual = dataReader.readFromFile(TEST_FILE_NAME);
        assertEquals(actual, expected);
    }

    @Test (expected = NullPointerException.class)
    public void readFileWithNull_NotOk() {
        dataReader.readFromFile(null);
    }

    @Test (expected = RuntimeException.class)
    public void readFileWithIncorrectWay_NotOk() {
        dataReader.readFromFile("src/test/resources/WrongDirectoryOfTestFile.csv");
    }
}
