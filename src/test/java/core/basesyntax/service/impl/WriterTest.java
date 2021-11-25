package core.basesyntax.service.impl;

import core.basesyntax.service.Writer;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterTest {
    private static Writer writer;

    @BeforeClass
    public static void init() {
        writer = new WriterImpl();
    }

    @Test
    public void write_Ok() {
        Assert.assertTrue(writer.write("src/main/resources/report.csv", "Test"));
    }

    @Test(expected = RuntimeException.class)
    public void writeInvalidPath_NotOk() {
        writer.write("", "Test");
    }

    @Test
    public void writeCorrectData_Ok() {
        String filePath = "src/main/resources/report.csv";
        writer.write(filePath, "Test");
        List<String> expected = List.of("Test");
        File fileToRead = new File(filePath);
        try {
            Assert.assertEquals(expected, Files.readAllLines(fileToRead.toPath()));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file " + filePath, e);
        }
    }
}
