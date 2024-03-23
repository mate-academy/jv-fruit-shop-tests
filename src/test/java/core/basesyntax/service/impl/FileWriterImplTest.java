package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.exception.CantWriteToFileException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String WRITE_TO_FILE_OK_PATH = "src\\resources\\writer_test_file";
    private static final String WRITE_TO_FILE_EMPTY_PATH = "";
    private static final FileWriterImpl fileWriter = new FileWriterImpl();

    @Test
    void write_ValidFileName_Ok() throws IOException {
        List<String> processedData = List.of("SomeString 1", "SomeString 2");
        boolean dataHasWritten = fileWriter.write(processedData, WRITE_TO_FILE_OK_PATH);

        assertTrue(dataHasWritten);
        assertTrue(Files.exists(Path.of(WRITE_TO_FILE_OK_PATH)));
        assertEquals(processedData, Files.readAllLines(Path.of(WRITE_TO_FILE_OK_PATH)));
    }

    @Test
    void write_EmptyData_Ok() throws IOException {
        List<String> processedData = List.of();
        boolean dataHasWritten = fileWriter.write(processedData, WRITE_TO_FILE_OK_PATH);

        assertTrue(dataHasWritten);
        assertTrue(Files.exists(Path.of(WRITE_TO_FILE_OK_PATH)));
        assertEquals(processedData, Files.readAllLines(Path.of(WRITE_TO_FILE_OK_PATH)));
    }

    @Test
    void write_NullFile_NotOk() {
        FileWriterImpl fileWriter = new FileWriterImpl();
        List<String> processedData = List.of("some_data");

        assertThrows(CantWriteToFileException.class, () -> fileWriter.write(processedData, null));
    }

    @Test
    void write_EmptyFile_NotOk() {
        FileWriterImpl fileWriter = new FileWriterImpl();
        List<String> processedData = List.of("some_data");

        assertThrows(CantWriteToFileException.class,
                () -> fileWriter.write(processedData, WRITE_TO_FILE_EMPTY_PATH));
    }

}
