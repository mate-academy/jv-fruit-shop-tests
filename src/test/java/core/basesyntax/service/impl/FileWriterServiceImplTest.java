package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String PATH = "src/test/resources/dataTo.csv";
    private static final String DATA_TO_WRITE = "Text ForWriteInFileImpl class tests";
    private static FileWriterService fileWriterService;

    @BeforeClass
    public static void setUp() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void writeToFile_Ok() {
        fileWriterService.writeToFile(DATA_TO_WRITE, PATH);
        String actual = readFile(PATH);
        assertEquals(DATA_TO_WRITE, actual);
    }

    @Test
    public void writeInFile_EmptyData_NotOk() {
        assertThrows(RuntimeException.class, () ->
                fileWriterService.writeToFile("", PATH));
    }

    @Test
    public void writeInFile_NullData_NotOk() {
        assertThrows(RuntimeException.class, () ->
                fileWriterService.writeToFile(null, PATH));
    }

    @Test
    public void writeToFile_InvalidPath_NotOk() {
        assertThrows(RuntimeException.class, () ->
                fileWriterService.writeToFile(DATA_TO_WRITE, ""));
    }

    @Test
    public void writeToFile_NullPath_NotOk() {
        assertThrows(RuntimeException.class, () ->
                fileWriterService.writeToFile(DATA_TO_WRITE, null));
    }

    private String readFile(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file: " + path, e);
        }
    }
}
