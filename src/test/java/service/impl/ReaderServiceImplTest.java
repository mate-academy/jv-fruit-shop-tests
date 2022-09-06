package service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.ReaderService;

public class ReaderServiceImplTest {
    private static final String CORRECT_PATH = "src/test/resources/test_input.csv";
    private static final String INCORRECT_PATH = "fwa/ss";
    private ReaderService readerService;
    
    @Before
    public void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void read_correctPath_ok() {
        try {
            Assert.assertEquals(Files.readAllLines(Path.of(CORRECT_PATH)),
                    readerService.readFrom(CORRECT_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test (expected = RuntimeException.class)
    public void read_incorrectPath_notOk() {
        readerService.readFrom(INCORRECT_PATH);
    }
}
