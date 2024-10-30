package core.basesyntax.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String HEADER = "type, fruit, quantity";
    private static final String NEW_DATA = "newType, newFruit, newQuantity";
    private static final String INVALID_FILE_PATH = "src/main/converter/valid_data.csv";
    private FileWriter fileWriter = new FileWriterImpl();

    @Test
    void writingData_aFile() throws IOException {
        String expected = "type, fruit, quantity";
        Path tempFile = Files.createTempFile("testfile", ".csv");

        fileWriter.write(expected, tempFile.toString());

        List<String> actual = Files.readAllLines(tempFile);
        assertEquals(Collections.singletonList(expected), actual);
    }

    @Test
    void writingEmptyData_createsEmptyOrNewlineFile() throws IOException {
        String expected = "";
        Path tempFile = Files.createTempFile("testfile", ".csv");

        fileWriter.write(expected, tempFile.toString());

        List<String> actual = Files.readAllLines(tempFile);
        assertEquals(1, actual.size(),
                "File should contain exactly one line");
        assertTrue(actual.get(0).isEmpty(), "The line should be empty");
    }

    @Test
    void writingToFile_overwritesPreviousData() throws IOException {
        String expected = HEADER;
        Path tempFile = Files.createTempFile("testfile", ".csv");

        fileWriter.write(expected, tempFile.toString());

        List<String> actual = Files.readAllLines(tempFile);
        assertEquals(Collections.singletonList(expected), actual);

        String expected2 = NEW_DATA;

        fileWriter.write(expected2, tempFile.toString());

        List<String> actual1 = Files.readAllLines(tempFile);
        assertEquals(Collections.singletonList(expected2), actual1);
    }

    @Test
    void writingToInvalidFilePath_throwsRuntimeException() {
        String expected = "type, fruit, quantity";
        assertThrows(RuntimeException.class, () -> {
            fileWriter.write(expected, INVALID_FILE_PATH);
        });
    }
}
