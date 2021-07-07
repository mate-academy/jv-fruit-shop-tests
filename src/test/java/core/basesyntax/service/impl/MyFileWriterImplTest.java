package core.basesyntax.service.impl;

import core.basesyntax.service.MyFileWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MyFileWriterImplTest {
    private static MyFileWriter writer;

    @BeforeClass
    public static void beforeClass() throws Exception {
        writer = new MyFileWriterImpl();
    }

    @Before
    public void setUp() throws Exception {
        PrintWriter writer = new PrintWriter("src/test/resourses/numbers.csv");
        writer.print("");
        writer.close();
    }

    @Test
    public void writer_writeToFile_ok() {
        String expected = "1234567";
        writer.writeToFile("src/test/resourses/numbers.csv", expected);
        File file = new File("src/test/resourses/numbers.csv");
        String actual = "";
        try {
            actual = Files.readString(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Can`t read testFile" + e);
        }
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writer_writeToFile_notOk() {
        writer.writeToFile("", "Hello");
    }
}
