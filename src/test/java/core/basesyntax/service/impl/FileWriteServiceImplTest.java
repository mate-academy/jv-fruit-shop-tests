package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriteService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriteServiceImplTest {
    private static FileWriteService fileWriteService;

    @BeforeClass
    public static void setUp() {
        fileWriteService = new FileWriteServiceImpl();
    }

    @Test
    public void writeToFile_validFilePath_ok() throws IOException {
        Path validPath = Path.of("src/test/resources/output.csv");
        String expected = "expected data";
        fileWriteService.writeToFile(validPath, expected);
        String actual = Files.readString(validPath);
        assertEquals(expected, actual);
    }

    @Test (expected = NullPointerException.class)
    public void writeToFile_nullFilePath_notOk() {
        fileWriteService.writeToFile(null, "");
    }

    @Test (expected = RuntimeException.class)
    public void writeToFile_nonValidFilePath_notOk() {
        fileWriteService.writeToFile(Path.of(""), "valid data");
    }
}