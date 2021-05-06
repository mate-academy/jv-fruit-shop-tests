package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import core.basesyntax.exceptions.IllegalDataException;
import org.junit.Test;

public class DataReaderTest {
    private static final DataReader dataReader = new DataReader();

    @Test
    public void dataReaderTest_Ok() throws IOException {
        List<String> expected = new ArrayList<>();
        expected.add("b,banana,10");
        expected.add("b,apple,20");
        List<String> actual = dataReader.readData("src/test/test_data.csv");
        assertEquals(expected, actual);
    }

    @Test(expected = IOException.class)
    public void handleIOException_Ok() throws IOException {
        dataReader.readData("file.csv");
    }
}
