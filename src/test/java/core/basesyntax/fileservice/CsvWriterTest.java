package core.basesyntax.fileservice;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Test;

public class CsvWriterTest {
    private static final CsvWriter cswWriter = new CsvWriter();
    private static final String DATA = "TEST_DATA,DATA_TEST";
    private static final String PATH_NAME = "testFile.csv";

    @Test
    public void validFile_Ok() {
        cswWriter.writeToFile(PATH_NAME, DATA);
        assertEquals(DATA, readFromFile());
    }

    @Test(expected = RuntimeException.class)
    public void fileNotFound_NotOk() {
        cswWriter.writeToFile("", DATA);
    }

    private String readFromFile() {
        StringBuilder actual = new StringBuilder();

        try (FileReader fileReader = new FileReader(PATH_NAME)) {
            BufferedReader reader = new BufferedReader(fileReader);
            String line = reader.readLine();

            while (line != null) {
                actual.append(line.trim());
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Problem with file " + PATH_NAME);
        }

        return actual.toString();
    }
}
