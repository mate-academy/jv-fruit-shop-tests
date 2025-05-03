package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileWriterServiceImplTest {
    private static FileWriterService fileWrite;
    @TempDir
    private Path tempDir;
    private Path tempFilePath;

    @BeforeEach
    void setUp() {
        tempFilePath = tempDir.resolve("finalReport.csv");
    }

    @BeforeAll
    static void beforeAll() {
        fileWrite = new FileWriterServiceImpl();
    }

    @Test
    void write_dataToFile_isOk() throws IOException {
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90";
        fileWrite.writeIntoFile(report, tempFilePath.toString());
        String actualContent = Files.readString(tempFilePath);
        Assertions.assertTrue(Files.exists(tempFilePath));
        assertEquals(report.length(), Files.size(tempFilePath));
        assertEquals(report, actualContent);
    }

    @Test
    void write_pathToFileNotCorrect_throwRuntimeException() {
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90";
        String incorrectPath = "incorrectPath/csv";
        Exception exception = Assertions.assertThrows(RuntimeException.class, () ->
                fileWrite.writeIntoFile(report, incorrectPath));
        String actual = exception.getMessage();
        String expected = "Error writing report to file at path: " + incorrectPath;
        assertEquals(expected, actual);
    }
}
