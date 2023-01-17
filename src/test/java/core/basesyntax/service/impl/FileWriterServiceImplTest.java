package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static FileWriterService fileWriterService;

    @BeforeClass
    public static void init() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void writeToFile_isOk() throws IOException {
        String validOutputPath = "src/test/resources/output_test_file.csv";
        String report = "report";
        fileWriterService.writeToFile(validOutputPath, report);
        String actual = Files.readString(Path.of(validOutputPath));
        assertEquals(report, actual);
    }

    @Test (expected = RuntimeException.class)
    public void writeToFile_writeToNonExistingFile_isNotOk() {
        fileWriterService.writeToFile("", "");
    }

    @Test (expected = NullPointerException.class)
    public void writeToFile_nullFilePath_isNotOk() {
        String report = "report";
        fileWriterService.writeToFile(null, "");
    }
}
