package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WriterServiceImplTest {
    private static WriterService writerService;
    private static final String FILE_NAME = "src/test/resources/writerServiceTest.csv";

    @Before
    public void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void createFile_ok() {
        writerService.writeReport("", FILE_NAME);
        File file = new File(FILE_NAME);
        Assert.assertTrue("Expected that file exists " + FILE_NAME, file.exists());
    }

    @Test
    public void writeData_ok() {
        String expected = "Hello world";
        writerService.writeReport(expected, FILE_NAME);
        List<String> actualDataFromFile = readLines(FILE_NAME);
        String actual = actualDataFromFile.get(0);
        Assert.assertEquals("Wrong data in file " + FILE_NAME, expected,
                actual);
    }

    @After
    public void tearDown() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
    }

    private List<String> readLines(String name) {
        try {
            return Files.readAllLines(new File(name).toPath());
        } catch (IOException e) {
            throw new RuntimeException("Can't read a file " + name, e);
        }
    }
}
