package core.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.exception.FileReadException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.Test;

public class ReadServiceImplTest {
    private ReadServiceImpl fileReader = new ReadServiceImpl();

    @Test
    public void read_ValidFile_Ok() {
        String tempFileName = "src/test/java/resources/temp_file.csv";
        String tempFileData = "Line 1\nLine 2\nLine 3";
        String expectedData = "Line 2" + System.lineSeparator() + "Line 3";
        createTemporaryFile(tempFileName, tempFileData);

        String dataFromFile = fileReader.read(tempFileName);
        assertEquals(dataFromFile, expectedData);

        deleteTemporaryFile(tempFileName);
    }

    private void createTemporaryFile(String fileName, String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteTemporaryFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void read_FileNotFound_ExceptionThrown() {
        FileReadException fileReadException = assertThrows(
                FileReadException.class, () -> fileReader.read(
                        "src/test/java/resources/nonexistent_file.csv"));
        assertEquals("File not found: src/test/java/resources/nonexistent_file.csv",
                fileReadException.getMessage());
    }

    @Test
    public void read_EmptyFile_EmptyStringReturned() {
        String dataFromFile = fileReader.read("src/test/java/resources/empty_input.csv");
        assertEquals("", dataFromFile);
    }

    @Test
    public void read_NullFileName_ExceptionThrown() {
        assertThrows(NullPointerException.class, () -> fileReader.read(null));
    }
}
