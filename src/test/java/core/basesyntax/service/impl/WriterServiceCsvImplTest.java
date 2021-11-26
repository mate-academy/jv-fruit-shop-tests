package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceCsvImplTest {
    private static WriterService writerService;
    private static File file;
    private static String filePath;
    private static Reader reader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        writerService = new WriterServiceCsvImpl();
        filePath = "src/test/resources/output.csv";
        file = new File(filePath);
        reader = new Reader();
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_wrongFilePath_ok() {
        String expected = "abc";
        String wrongFilePath = "";
        writerService.writeToFile(expected, wrongFilePath);
    }

    @Test
    public void writeToFile_ok() {
        String expected = "abc";
        writerService.writeToFile(expected, filePath);
        List<String> readData = reader.readFile(file);
        StringBuilder stringBuilder = new StringBuilder();
        readData.forEach(stringBuilder::append);
        String actual = stringBuilder.toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void writeToFile_emptyData_ok() {
        String expected = "";
        writerService.writeToFile(expected, filePath);
        List<String> readData = reader.readFile(file);
        StringBuilder stringBuilder = new StringBuilder();
        readData.forEach(stringBuilder::append);
        String actual = stringBuilder.toString();
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_nullToWrite_notOk() {
        writerService.writeToFile(null, filePath);
    }

    private static class Reader {
        private List<String> readFile(File file) {
            try {
                List<String> strings = Files.readAllLines(file.toPath());
                return strings;
            } catch (IOException e) {
                throw new RuntimeException("Can't read a file");
            }
        }
    }
}
