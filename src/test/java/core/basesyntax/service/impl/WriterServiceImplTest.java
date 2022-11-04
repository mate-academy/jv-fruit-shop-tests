package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String VALID_INPUT_FILEPATH = "src/test/"
            + "java/core/basesyntax/resources/test_writeToFile.csv";
    private static final String INVALID_INPUT_FILEPATH = "";
    private static final String NOT_EXISTING_INPUT_FILEPATH = "src/test/"
            + "java/core/basesyntax/resources/test_writeToFileNotExisting.csv";
    private static final String INPUT_STRING =
            "fruit,quantity" + System.lineSeparator() + "apple,10";
    private WriterService writerService;

    @Before
    public void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void writeToFile_ValidFilepath_Ok() throws IOException {
        writerService.writeToFile(INPUT_STRING,VALID_INPUT_FILEPATH);
        List<String> expected = List.of(INPUT_STRING.split(System.lineSeparator()));
        List<String> actual = Files.readAllLines(Path.of(VALID_INPUT_FILEPATH));
        Assert.assertEquals(expected,actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_InvalidFilepath_notOk() {
        writerService.writeToFile(INPUT_STRING,INVALID_INPUT_FILEPATH);
    }

    @Test()
    public void writeToFile_NonExistingFilepath_notOk() throws IOException {
        writerService.writeToFile(INPUT_STRING,NOT_EXISTING_INPUT_FILEPATH);
        List<String> expected = List.of(INPUT_STRING.split(System.lineSeparator()));
        Path nonExistingFilePath = Path.of(NOT_EXISTING_INPUT_FILEPATH);
        List<String> actual = Files.readAllLines(nonExistingFilePath);
        Assert.assertEquals(expected,actual);
        Files.delete(nonExistingFilePath);
    }
}
