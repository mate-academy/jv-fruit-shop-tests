package core.basesyntax.service;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class CsvFileReaderTest {
    private static final String PATH_FILE_INTO = "src/main/resources/storage.csv";
    private CsvFileReader fileReader;

    @Before
    public void setFileReader() {
        fileReader = new CsvFileReader();
    }

    @Test
    public void fileReader_readSuccess_ok() {
        List<String> expected = new ArrayList<>();
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50");
        List<String> actual = fileReader.readFromFile(PATH_FILE_INTO);
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void fileReader_failRead_notOk() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50");
        List<String> actual = fileReader.readFromFile(PATH_FILE_INTO);
        assertNotEquals(expected.toArray(), actual.toArray());
    }

    @Test(expected = RuntimeException.class)
    public void fileReader_nullArgPath() {
        fileReader.readFromFile("sfkjdh.csv");
    }

}
