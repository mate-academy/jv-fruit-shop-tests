package serviceimpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import service.FileWriter;

public class FileWriterImplTest {
    private static FileWriter fileWriter;
    private static final String CORRECT_REPORT_PATH = "src/main/resources/CloseDay.csv";
    private static final String WRONG_REPORT_PATH = "src/main/resources";
    private static String expectedString;

    @BeforeClass
    public static void beforeClass() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void fileWriter_Ok() {
        expectedString = "fruits, quantity" + System.lineSeparator()
                + "banana" + "," + "152" + System.lineSeparator()
                + "apple" + "," + "90";
        String filePath = CORRECT_REPORT_PATH;
        fileWriter.writeToFile(filePath, expectedString);
        String actual = getReadFile(filePath);
        Assert.assertEquals(expectedString, actual);
    }

    @Test (expected = RuntimeException.class)
    public void write_wrongPath_NotOk() {
        fileWriter.writeToFile(expectedString,WRONG_REPORT_PATH);
    }

    @Test (expected = RuntimeException.class)
    public void write_toFileNull_notOk() {
        fileWriter.writeToFile(null, CORRECT_REPORT_PATH);
    }

    @Test (expected = RuntimeException.class)
    public void wayToReportIsNull_notOk() {
        fileWriter.writeToFile(expectedString, null);
    }

    public String getReadFile(String fileName) {
        String lines;
        try {
            lines = Files.readString(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't read a file " + fileName, e);
        }
        return lines;
    }
}
