package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static String filePath;
    private static WriterService writer;
    private static String text;

    @BeforeClass
    public static void beforeClass() {
        filePath = "src/test/java/resources/testForWriter.csv";
        writer = new WriterServiceImpl();
        text = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90" + System.lineSeparator();
    }

    @Test
    public void write_validPath_ok() {
        writer.writeToFile(filePath, text);
        String actual;
        try {
            actual = Files.readString(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Test with Writer failed" + e);
        }
        String expected = text;
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void write_invalidPath_notOk() {
        writer.writeToFile("src/test/java/ resources/testForWriter.csv", text);
    }

    @Test(expected = RuntimeException.class)
    public void write_invalidData_notOk() {
        writer.writeToFile(filePath, null);
    }

    @Test(expected = RuntimeException.class)
    public void write_nullPath_notOk() {
        writer.writeToFile(null, text);
    }
}
