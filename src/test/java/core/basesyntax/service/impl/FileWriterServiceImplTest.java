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
    public static void setUp() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void writeToFile_validFilePath_ok() throws IOException {
        Path validPath = Path.of("src/test/resources/output.csv");
        String expected = "expected data";
        fileWriterService.writeToFile(validPath, expected);
        String actual = Files.readString(validPath);
        assertEquals(expected, actual);
    }

    @Test (expected = NullPointerException.class)
    public void writeToFile_nullFilePath_notOk() {
        fileWriterService.writeToFile(null, "");
    }

    @Test (expected = RuntimeException.class)
    public void writeToFile_nonValidFilePath_notOk() {
        fileWriterService.writeToFile(Path.of(""), "valid data");
    }
}
