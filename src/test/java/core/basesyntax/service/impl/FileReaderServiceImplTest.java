package core.basesyntax.service.impl;

import core.basesyntax.service.FileReaderService;
import java.nio.file.Path;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String RESOURCES_PATH = "src/test/resources/";
    private static FileReaderService reader;

    @Test(expected = RuntimeException.class)
    public void readFile_withNullPath_notOk() {
        reader = new FileReaderServiceImpl(null);
        reader.readFile();
    }

    @Test(expected = RuntimeException.class)
    public void readFile_withUnexistedFile_notOk() {
        reader = new FileReaderServiceImpl(Path.of(RESOURCES_PATH + "unexisting.csv"));
        reader.readFile();
    }

    @Test
    public void readFile_withExistingFile_Ok() {
        reader = new FileReaderServiceImpl(Path.of(RESOURCES_PATH + "input.csv"));
        List<String> actualReaderResult = reader.readFile();
        List<String> expectedReaderResult = List.of("line1Header", "line2Content", "line3Content");
        Assert.assertEquals(expectedReaderResult, actualReaderResult);
    }
}
