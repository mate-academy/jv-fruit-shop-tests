package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String CORRECT_FILE_PATH = "src/test/resources/output.csv";
    private static final String WRONG_FILE_PATH = "google/output.xml";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static WriterService writerService;
    private static String data;

    @BeforeClass
    public static void beforeClass() {
        writerService = new FileWriterServiceImpl();
        data = "fruit,quantity"
                + LINE_SEPARATOR + "banana,152"
                + LINE_SEPARATOR + "apple,90";
    }

    @Test
     public void writeToFile_correctPath_ok() {
        writerService.write(data, CORRECT_FILE_PATH);
        List<String> dataFromFile;
        try {
            dataFromFile = Files.readAllLines(Path.of(CORRECT_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file " + CORRECT_FILE_PATH, e);
        }
        List<String> expectedData = new ArrayList<>(Arrays.asList(data.split(LINE_SEPARATOR)));
        Assert.assertEquals(expectedData, dataFromFile);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_incorrectPath_notOk() {
        writerService.write(data, WRONG_FILE_PATH);
    }
}
