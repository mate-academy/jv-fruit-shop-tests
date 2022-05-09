package service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import service.impl.WriterServiceImpl;

public class WriterServiceTest {
    private static WriterService writerService;
    private static final String OUTPUT_FILE = "src/test/resources/after.csv";
    private static final String WRONG_OUTPUT_FILE = "folder/pictures/after.csv";
    private static String expected = "";

    @BeforeClass
    public static void beforeClass() throws Exception {
        writerService = new WriterServiceImpl();
        expected = "fruit,quantity"
                + System.lineSeparator() + "banana,152"
                + System.lineSeparator() + "apple,90";
    }

    @Test
    public void writerService_CorrectPath_OK() {
        writerService.write(OUTPUT_FILE, expected);
        String actual = read();
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeService_WrongPath_NotOK() {
        writerService.write(WRONG_OUTPUT_FILE, expected);
    }

    private String read() {
        StringBuilder readData = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(OUTPUT_FILE))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                readData.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException("File " + OUTPUT_FILE + " not found", e);
        }
        return readData.toString().trim();
    }
}
