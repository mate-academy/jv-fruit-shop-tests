package core.basesyntax.service.impl;

import core.basesyntax.service.FileReaderService;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FileReaderServiceTest {
    private FileReaderService fileReaderService;

    @Before
    public void setUp() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void read_pathIsValid_ok() {
        Path path = Path.of("src/test/resources/testInputFile.csv");
        List<String> expected = List.of("b,banana,100", "s,banana,200");
        List<String> actual = fileReaderService.read(path);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void read_pathIsInvalid_ok() {
        fileReaderService.read(null);
    }
}
