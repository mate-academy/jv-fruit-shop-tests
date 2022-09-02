package core.basesyntax.service;

import core.basesyntax.service.impl.ReaderImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderImplTest {
    private static final String PATH = "src/test/resources/test.csv";
    private static final String MESSAGE = "Hello, mates!";
    private static Reader reader;

    @BeforeClass
    public static void beforeClass() {
        reader = new ReaderImpl();
    }

    @Before
    public void setUp() {
        try {
            Files.write(Path.of(PATH), MESSAGE.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Can't create file: ", e);
        }
    }

    @Test
    public void reader_readDataFromFile_Ok() {
        List<String> information = new ReaderImpl().read(PATH);
        Assert.assertEquals(MESSAGE, information.get(0));
    }

    @Test(expected = RuntimeException.class)
    public void reader_readNotExistFile_notOk() {
        reader.read("txest.csv");
    }

    @Test(expected = RuntimeException.class)
    public void reader_fileNameIsNull_notOk() {
        reader.read(null);
    }
}
