package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class DataReaderTest {
    private static final DataReader dataReader = new DataReader();

    @Test
    public void dataReaderTest_Ok() {
        List<String> expected = new ArrayList<>();
        expected.add("b,banana,10");
        expected.add("b,apple,20");
        List<String> actual = dataReader.readData("src/test/test_data.csv");
        assertEquals(expected, actual);
    }
}
