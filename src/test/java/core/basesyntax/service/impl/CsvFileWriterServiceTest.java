package core.basesyntax.service.impl;

import core.basesyntax.exceptions.WriteDataToFileException;
import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Assertions;
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
    void writeDataToFile_ExistingFile_Ok() {
        String actual = writeAndAfterReadFromFile(RESULT_FILE_EXISTING, TEXT_TO_WRITING);
        Assertions.assertEquals(TEXT_TO_WRITING, actual);
    }

    @Test
    void writeDataToFile_NotExistingFile_Ok() {
        Path path = Paths.get(RESULT_FILE_NOT_EXISTING);
        if (Files.exists(path) && Files.isRegularFile(path)) {
            path.toFile().delete();
        }
        String actual = writeAndAfterReadFromFile(RESULT_FILE_NOT_EXISTING, TEXT_TO_WRITING);
        Assertions.assertEquals(TEXT_TO_WRITING, actual);
    }

    @Test
    void writeDataToFile_CleanupBeforeWriting_Ok() {
        fileWriter.writeDataToFile(TEXT_TO_WRITING, RESULT_FILE_EXISTING);
        fileWriter.writeDataToFile(TEXT_TO_WRITING, RESULT_FILE_EXISTING);
        String actual = readFromFile(RESULT_FILE_EXISTING);
        Assertions.assertEquals(TEXT_TO_WRITING, actual);
    }

    @Test
    void writeDataToFile_notExistingDirectory_NotOk() {
        WriteDataToFileException exception = null;
        try {
            fileWriter.writeDataToFile(TEXT_TO_WRITING, NOT_EXISTING_DIRECTORY_NAME);
        } catch (WriteDataToFileException e) {
            exception = e;
        }
        String expected = "Can't write db to file" + NOT_EXISTING_DIRECTORY_NAME;
        Assertions.assertEquals(expected, exception.getMessage());
    }

    private String readFromFile(String fileName) {
        try {
            return Files.readString(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly read data from file " + fileName, e);
        }
    }

    private String writeAndAfterReadFromFile(String fileName, String text) {
        fileWriter.writeDataToFile(text, fileName);
        return readFromFile(fileName);
    }
}
