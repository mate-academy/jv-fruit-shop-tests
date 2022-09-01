package core.basesyntax.service;

import core.basesyntax.service.impl.ReaderImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReaderImplTest {
    @Before
    public void setUp() {
        try {
            Files.write(Path.of("src/test/resources/test.csv"), "Hello, mates!".getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Can't create file: ", e);
        }
    }

    @Test
    public void reader_readDataFromFile_Ok() {
        List<String> information = new ReaderImpl().read("src/test/resources/test.csv");
        Assert.assertEquals("Hello, mates!", information.get(0));
    }

    @Test(expected = RuntimeException.class)
    public void reader_readNotExistFile_notOk() {
        new ReaderImpl().read("test.csv");
    }

    @Test(expected = RuntimeException.class)
    public void reader_fileNameIsNull_notOk() {
        new ReaderImpl().read(null);
    }
}
