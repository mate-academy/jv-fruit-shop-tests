package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.CsvFileReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileReaderImplTest {
    private static CsvFileReader fileReader;

    @BeforeClass
    public static void setUp() {
        fileReader = new CsvFileReaderImpl();
    }

    @Test(expected = RuntimeException.class)
    public void readData_emptyPath_notOk() {
        fileReader.readData("");
    }

    @Test(expected = RuntimeException.class)
    public void readData_nullPath_notOk() {
        fileReader.readData(null);
    }

    @Test
    public void readData_validPath_Ok() {
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
        List<String> actual = fileReader.readData("src/main/resources/file.csv");
        assertEquals(expected, actual);
    }
}
