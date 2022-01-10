package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import java.util.List;
import org.junit.After;
import org.junit.Test;

public class FileWriterToFileTest {
    private FileReaderFromFile fileReaderFromFile = new FileReaderFromFile();
    private FileWriterToFile fileWriterToFile = new FileWriterToFile();

    @After
    public void setUp() {
        Storage.fruits.clear();
    }

    @Test
    public void contains_Ok() {
        List<String> expected = List.of("fruit,quantity", "apple,90", "banana,152");
        String report = "fruit,quantity\n"
                + "apple,90\n"
                + "banana,152\n";
        fileWriterToFile.writeToFile("testFile", report);
        List<String> actual = fileReaderFromFile.readFromFile("testFile");
        assertEquals(expected, actual);
    }
}
