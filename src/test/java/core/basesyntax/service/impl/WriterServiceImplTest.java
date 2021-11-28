package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class WriterServiceImplTest {
    private static String filePath;
    private static WriterService writer;
    private static String text;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        filePath = "src/test/java/resources/testForWriter.csv";
        writer = new WriterServiceImpl();
        text = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90" + System.lineSeparator();
    }

    @Test
    public void write_validPath_Ok() {
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

    @Test
    public void write_invalidPath_Ok() {
        thrown.expect(RuntimeException.class);
        writer.writeToFile("src/test/java/ resources/testForWriter.csv", text);
    }

    @Test
    public void write_invalidData_Ok() {
        thrown.expect(RuntimeException.class);
        writer.writeToFile(filePath, null);
    }

    @Test
    public void write_nullPath_Ok() {
        thrown.expect(RuntimeException.class);
        writer.writeToFile(null, text);
    }
}
