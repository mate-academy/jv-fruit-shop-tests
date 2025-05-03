package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private WriterService writerService;
    private Path tempFile;

    @BeforeEach
    void setUp() throws IOException {
        writerService = new WriterServiceImpl();
        tempFile = Files.createTempFile("testFile", ".txt");
    }

    @Test
    void writeValidData_Ok() throws IOException {
        String actual = "apple,10\nbanana,5\norange,8";
        writerService.writeToFile(actual, tempFile.toString());

        String expected = Files.readString(tempFile);
        assertEquals(actual, expected);
    }

    @Test
    void overwriteExistingFile_Ok() throws IOException {
        Files.writeString(tempFile, "Old data");

        String newDataFile = "new data";
        writerService.writeToFile(newDataFile, tempFile.toString());

        String actualFile = Files.readString(tempFile);
        assertEquals(newDataFile, actualFile);
    }

    @Test
    void nonWritablePath_Failure() {
        String invalidPath = "/invalidPath/testFile.txt";
        Exception exception = assertThrows(RuntimeException.class,
                () -> writerService.writeToFile("data", invalidPath));
        assertTrue(exception.getMessage().contains("Can't write to file"));
    }

    @Test
    void writeEmptyString_Ok() throws IOException {
        writerService.writeToFile(" ", tempFile.toString());
        String actualFile = Files.readString(tempFile);
        assertEquals(" ", actualFile);
    }

    @Test
    void writeNull_NotOk() {
        Exception exception = assertThrows(RuntimeException.class,
                () -> writerService.writeToFile(null, tempFile.toString()));
        assertNotNull(exception);
    }
}
