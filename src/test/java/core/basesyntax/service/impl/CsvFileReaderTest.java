package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.CsvFileReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class CsvFileReaderTest {
    private static final String validFile = "src/test/resources/validFile.csv";
    private static final String emptyFile = "src/test/resources/emptyFile.csv";
    private static final String nonExistingFile
            = "src/test/resources/nonExistingFile.csv";
    private final CsvFileReader reader = new CsvFileReaderImpl();
    private final List<String> expectedData = fillExpectedDataArray();

    @Test
    public void read_existingValidFile_Ok() {
        List<String> readData = reader.read(validFile);
        assertEquals(expectedData, readData);
    }

    @Test
    public void read_existingEmptyFile_notOk() {
        assertThrows(RuntimeException.class,
                () -> reader.read(emptyFile), "Empty CSV file");
    }

    @Test
    public void read_nonExistingFile_notOk() {
        assertThrows(RuntimeException.class,
                () -> reader.read(nonExistingFile), "Cannot read file nonExistingFile.csv");
    }

    private List<String> fillExpectedDataArray() {
        List<String> data = new ArrayList<>();
        data.add("type,fruit,quantity");
        data.add("b,banana,20");
        data.add("b,apple,100");
        data.add("s,banana,100");
        data.add("p,banana,13");
        data.add("r,apple,10");
        data.add("p,apple,20");
        data.add("p,banana,5");
        data.add("s,banana,50");
        return data;
    }
}
