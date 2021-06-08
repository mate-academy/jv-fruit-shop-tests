package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class FileReaderTest {
    private FileReader fileReader = new FileReader();

    @Test
    public void readDataFromFile_Ok() {
        String fileName = "database.csv";
        List<String> expected = List.of("type,fruit,quantity", "b,banana,10", "b,apple,100",
                "s,banana,100", "p,banana,13", "r,apple,10", "p,apple,20",
                "p,banana,5", "s,banana,50");
        List<String> actual = fileReader.readDataFromFile(fileName);
        assertEquals("You read incorrectly data from file!", expected, actual);
    }

    @Test
    public void pathToFileIsNull_NotOk() {
        String fileName = "";
        Assertions.assertThrows(RuntimeException.class, () -> {
            fileReader.readDataFromFile(fileName);
        });
    }
}
