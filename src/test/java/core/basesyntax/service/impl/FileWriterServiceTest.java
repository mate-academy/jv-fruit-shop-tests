package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FileWriterServiceTest {
    private FileWriterService fileWriterService;

    @Before
    public void setUp() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void write_validPathToWriteFile_Ok() {
        String dataToWrite = "The quick brown fox jumps over the lazy dog";
        Path path = Path.of("src/test/resources/testOutputFile.csv");
        fileWriterService.write(path, dataToWrite);
        List<String> actual = fileReader(path);
        List<String> expected = List.of(dataToWrite);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void write_invalidPathToWriteFile_ExceptionThrown() {
        String dataToWrite = "The quick brown fox jumps over the lazy dog";
        Path path = null;
        fileWriterService.write(path, dataToWrite);
    }

    private List<String> fileReader(Path path) {
        List<String> dataFromFile;
        try {
            dataFromFile = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException("Can't read file from: " + path, e);
        }
        return dataFromFile;
    }
}
