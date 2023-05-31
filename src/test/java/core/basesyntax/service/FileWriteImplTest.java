package core.basesyntax.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileWriteImplTest {
    public static final String FILE_NAME = "src/test/java/core/basesyntax/resources/test.txt";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private FileWrite fileWrite;

    @Before
    public void beforEachTest() {
        fileWrite = new FileWriteImpl();
    }

    public String actualData() {
        StringBuilder stringBuilder = new StringBuilder();
        String string = "banana,150\napple,150\nananas,150\n";
        fileWrite.writeDataToFile(string, FILE_NAME);
        try (BufferedReader bufferReader =
                     new BufferedReader(
                             new java.io.FileReader(
                                     FILE_NAME))) {
            String value = bufferReader.readLine();
            while (value != null) {
                stringBuilder.append(value)
                        .append("\n");
                value = bufferReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("File cannot be read");
        }
        return stringBuilder.toString();
    }

    @Test
    public void writeDataToFile_correctData_ok() {
        String actual = actualData();
        String expected = "banana,150\napple,150\nananas,150\n";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void writeDataToFile_inCorrectPath_notOk() {
        String file = "";
        String testData = "I love Java";
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("File can not be written " + Path.of(file));
        fileWrite.writeDataToFile(testData, file);
    }
}
