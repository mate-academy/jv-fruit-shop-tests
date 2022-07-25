package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.Test;

public class FileReaderCsvImplTest {
    private static final String FILENAME = "src/test/resources/testReaderFile.csv";
    private static final String NON_EXISTENT_FILENAME =
                                "src/test/resources/testReaderNonExistentFile.csv";
    private final FileReader fileReader = new FileReaderCsvImpl();

    @Test
    public void readFromGoodFile_Ok() throws Exception {
        String expectedLines = "type,fruit,quantity\n"
                + "b,banana,20\n"
                + "b,apple,100\n"
                + "s,banana,100\n"
                + "p,banana,13\n"
                + "p,apple,10\n"
                + "s,apple,20\n"
                + "p,banana,5\n"
                + "s,banana,50";

        try (FileWriter fileWriter = new FileWriter(FILENAME)) {
            fileWriter.write(expectedLines);
        } catch (IOException e) {
            throw new IOException("Can't write file to " + FILENAME, e);
        }

        List<String> lines = fileReader.readFromFile(FILENAME);
        String actualLines = String.join("\n", lines);

        assertEquals(expectedLines, actualLines,
                "Expected lines from file don't equals read readFromFile method");
    }

    @Test
    public void readFromEmptyFile_Ok() throws Exception {
        String expectedLines = "";

        try (FileWriter fileWriter = new FileWriter(FILENAME)) {
            fileWriter.write(expectedLines);
        } catch (IOException e) {
            throw new IOException("Can't write file to " + FILENAME, e);
        }

        List<String> lines = fileReader.readFromFile(FILENAME);
        String actualLines = String.join("\n", lines);

        assertEquals(expectedLines, actualLines,
                "Expected lines from file don't equals read readFromFile method");
    }

    @Test
    public void readFromNonExistentFile_NotOk() {
        RuntimeException thrown = assertThrows(RuntimeException.class, () ->
                fileReader.readFromFile(NON_EXISTENT_FILENAME),
                "Exception is expecting");
        assertEquals("Can't read from file " + NON_EXISTENT_FILENAME, thrown.getMessage());
    }
}
