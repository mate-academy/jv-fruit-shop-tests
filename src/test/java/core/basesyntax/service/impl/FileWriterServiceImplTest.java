package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileWriterServiceImplTest {
    private FileWriterService fileWrite = new FileWriterServiceImpl();

    @TempDir
    private Path tempDir;
    private Path tempFilePath;

    @BeforeEach
    void setUp() {
        tempFilePath = tempDir.resolve("finalReport.csv");
    }

    @Test
    void write_dataToFile_isOk() throws IOException {
        String report = "fruit,quantity\n"
                + "banana,152\n"
                + "apple,90";
        fileWrite.writeIntoFile(report, tempFilePath.toString());
        String actualContent = Files.readString(tempFilePath);
        Assertions.assertTrue(Files.exists(tempFilePath));
        Assertions.assertEquals(report.length(), Files.size(tempFilePath));
        Assertions.assertEquals(report, actualContent);
    }

    @Test
    void write_pathToFileNotCorrect_throwRuntimeException() {
        String report = "fruit,quantity\n"
                + "banana,152\n"
                + "apple,90";
        String incorrectPath = "incorrectPath/csv";
        Exception exception = Assertions.assertThrows(RuntimeException.class, () ->
                fileWrite.writeIntoFile(report, incorrectPath));
        String actual = exception.getMessage();
        String expected = "Error writing report to file at path: " + incorrectPath;
        Assertions.assertEquals(expected, actual);
    }
}
