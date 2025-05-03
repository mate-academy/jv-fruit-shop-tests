package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.WriteDataToFileException;
import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

class CsvFileWriterServiceTest {
    private static final String RESULT_FILE_EXISTING = FileSystems.getDefault()
            .getPath("src/test/resources/processedDataTest.csv").toString();
    private static final String NOT_EXISTING_DIRECTORY_NAME = FileSystems.getDefault()
            .getPath("src/test/unExistingFolder/processedDataTest.csv").toString();
    private static final String RESULT_FILE_NOT_EXISTING = FileSystems.getDefault()
            .getPath("src/test/resources/newProcessedDataTest.csv").toString();
    private static final String TEXT_TO_WRITING = "This is message for test!!!";
    private static final FileWriterService fileWriter = new CsvFileWriterService();

    @Test
    void writeDataToFile_existingFile_ok() throws IOException {
        String actual = writeAndReadFromFile(RESULT_FILE_EXISTING, TEXT_TO_WRITING);

        assertEquals(TEXT_TO_WRITING, actual);
    }

    @Test
    void writeDataToFile_notExistingFile_ok() throws IOException {
        Path path = Paths.get(RESULT_FILE_NOT_EXISTING);
        if (Files.exists(path) && Files.isRegularFile(path)) {
            path.toFile().delete();
        }

        String actual = writeAndReadFromFile(RESULT_FILE_NOT_EXISTING, TEXT_TO_WRITING);

        assertEquals(TEXT_TO_WRITING, actual);
    }

    @Test
    void writeDataToFile_cleanupBeforeWriting_ok() throws IOException {
        fileWriter.writeDataToFile(TEXT_TO_WRITING, RESULT_FILE_EXISTING);
        fileWriter.writeDataToFile(TEXT_TO_WRITING, RESULT_FILE_EXISTING);

        String actual = readFromFile(RESULT_FILE_EXISTING);

        assertEquals(TEXT_TO_WRITING, actual);
    }

    @Test
    void writeDataToFile_notExistingDirectory_notOk() {
        WriteDataToFileException exception = assertThrows(WriteDataToFileException.class,
                () -> fileWriter.writeDataToFile(TEXT_TO_WRITING, NOT_EXISTING_DIRECTORY_NAME));

        String expected = "Can't write data to file " + NOT_EXISTING_DIRECTORY_NAME;

        assertEquals(expected, exception.getMessage());
    }

    private String readFromFile(String fileName) throws IOException {
        return Files.readString(Path.of(fileName));
    }

    private String writeAndReadFromFile(String fileName, String text) throws IOException {
        fileWriter.writeDataToFile(text, fileName);
        return readFromFile(fileName);
    }
}
