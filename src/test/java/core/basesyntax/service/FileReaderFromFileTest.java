package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import java.util.List;
import org.junit.After;
import org.junit.Test;

public class FileReaderFromFileTest {
    private FileReaderFromFile fileReaderFromFile = new FileReaderFromFile();

    @After
    public void setUp() {
        Storage.fruits.clear();
    }

    @Test
    public void containsData_Ok() {
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20", "b,apple,100",
                "s,banana,100", "p,banana,13", "r,apple,10",
                "p,apple,20", "p,banana,5", "s,banana,50");
        List<String> actual = fileReaderFromFile.readFromFile("operations.csv");
        assertEquals(expected, actual);

    }
}
