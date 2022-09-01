package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static WriterService writerService;
    private static final String FILE_NAME = "src/test/resources/writerServiceTest.csv";

    @BeforeClass
    public static void beforeClass() {
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
        writerService.writeReport("Hello world", FILE_NAME);
        List<String> dataFromFile;
        try {
            dataFromFile = Files.readAllLines(new File(FILE_NAME).toPath());
        } catch (IOException e) {
            throw new RuntimeException("Can't read a file " + FILE_NAME, e);
        }
        Assert.assertEquals("Wrong data in file " + FILE_NAME, "Hello world", dataFromFile.get(0));
    }

    @After
    public void tearDown() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
    }
}
