package core.basesyntax.service.impl;

import core.basesyntax.service.ReadFileService;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Path;

public class ReadFileServiceImplTest {
    ReadFileService readFileService = new ReadFileServiceImpl();

    @Test
    public void readFromFile_validPath_ok() {
        String expected = "Just some string";
        String actual = readFileService.readFromFile(Path.of("src/test/resources/test_data.csv"));
        Assert.assertEquals("Strings must be equals", expected, actual);
    }

    @Test
    public void readFromFile_invalidPath_notOk() {

    }

}