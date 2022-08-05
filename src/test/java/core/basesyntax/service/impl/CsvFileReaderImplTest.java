package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.CsvFileReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class CsvFileReaderImplTest {
    private CsvFileReader fileReader;

    @Before
    public void setUp() {
        fileReader = new CsvFileReaderImpl();
    }

    @Test
    public void readData_emptyPath_notOk() {
        try {
            fileReader.readData("");
        } catch (RuntimeException e) {
            return;
        }
        fail("You can't read data from empty path");
    }

    @Test
    public void readData_nullPath_notOk() {
        try {
            fileReader.readData(null);
        } catch (RuntimeException e) {
            return;
        }
        fail("You can't read data from null path");
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
