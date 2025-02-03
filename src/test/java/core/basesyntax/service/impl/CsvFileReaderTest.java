package core.basesyntax.service.impl;

import core.basesyntax.service.CsvFileReader;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CsvFileReaderTest {
    private static final String VALID_FILE = "src/main/resources/testFiles/validFile.csv";
    private static final String EMPTY_FILE = "src/main/resources/testFiles/emptyFile.csv";
    private static final String NON_EXISTING_FILE
            = "src/main/resources/testFiles/nonExistingFile.csv";
    private final CsvFileReader reader = new CsvFileReaderImpl();
    private final List<String> expectedData = fillExpectedDataArray();

    @Test
    public void read_existingValidFile_Ok() {
        List<String> readData = reader.read(VALID_FILE);
        assertEquals(expectedData, readData);
    }

    @Test
    public void read_existingEmptyFile_notOk() {
        assertThrows(RuntimeException.class,
                () -> reader.read(EMPTY_FILE), "Empty CSV file");
    }

    @Test
    public void read_nonExistingFile_notOk() {
        assertThrows(RuntimeException.class,
                () -> reader.read(NON_EXISTING_FILE), "Cannot read file nonExistingFile.csv");
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
