package core.basesyntax.service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static FileWriter writer;

    @BeforeClass
    public static void beforeClass() {
        writer = new FileWriterImpl();
    }

    @Before
    public void setUp() throws Exception {
        PrintWriter writer = new PrintWriter("src/test/resources/numbers.csv");
        writer.print("");
        writer.close();
    }

    @Test
    public void writer_writeToFile_OK() {
        String expected = "12345678";
        writer.writeToFile("src/test/resources/numbers.csv", expected);
        File file = new File("src/test/resources/numbers.csv");
        String actual = "";
        try {
            actual = Files.readString(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file", e);
        }
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writer_incorrectFileName_not_OK() {
        writer.writeToFile("", "Some report");
    }
}
