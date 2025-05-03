package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.DataWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

class DataWriterImpTest {
    private static final String DATA_RESULT_FILE = "src/main/resources/data.csv";
    private final DataWriter dataWriter = new DataWriterImpl();

    @AfterAll
    static void clearResults() {
        try {
            Files.deleteIfExists(Path.of(DATA_RESULT_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly clear result files after test ", e);
        }
    }

    @Test
    void write_data_ok() {
        List<String> data = new ArrayList<>();
        data.add("banana,9");
        data.add("apple,7");
        dataWriter.write(data, DATA_RESULT_FILE);
        String actualResult = readFromFile(DATA_RESULT_FILE).trim();
        String expectedResult = "banana,9" + System.lineSeparator()
                + "apple,7";
        assertEquals(expectedResult, actualResult);
    }

    private String readFromFile(String fileName) {
        try {
            return Files.readString(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly read data from file " + fileName, e);
        }
    }
}
