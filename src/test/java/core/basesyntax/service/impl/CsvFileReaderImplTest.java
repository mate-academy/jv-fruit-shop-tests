package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CsvFileReaderImplTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void readFile_Ok() {
        String filePath = "src/main/java/core/basesyntax/resources/Input.csv";
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
        List<String> actual = new CsvFileReaderImpl().readFromFile(filePath);
        assertEquals(expected, actual);
    }

    @Test
    public void readFile_notOk() {
        String filePath = "invalidFilePath@ukrnet.ua";
        thrown.expectMessage("Can't read from file \"" + filePath + "\"");
        new CsvFileReaderImpl().readFromFile(filePath);
    }
}
