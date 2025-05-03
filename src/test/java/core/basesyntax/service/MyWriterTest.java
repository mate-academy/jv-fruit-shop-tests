package core.basesyntax.service;

import core.basesyntax.service.impl.MyWriterImpl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MyWriterTest {
    private MyWriter writer;

    @Before
    public void setUp() {
        writer = new MyWriterImpl();
    }

    @Test
    public void writeToFile_validData_ok() {
        String expected = "Some_report";
        writer.writeToFile("src/test/resources/reportTest.csv", "Some_report");
        StringBuilder builder = new StringBuilder();
        File file = new File("src/test/resources/reportTest.csv");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String value = reader.readLine();
            while (value != null) {
                builder.append(value);
                value = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file", e);
        }
        String actual = builder.toString();
        Assert.assertEquals(actual, expected);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_invalidFilePath_notOk() {
        writer.writeToFile("", "Some_report");
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_nullInputValue_notOk() {
        writer.writeToFile("src/test/resources/reportTest.csv", null);
    }
}
