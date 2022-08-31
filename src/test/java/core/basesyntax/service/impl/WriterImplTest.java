package core.basesyntax.service.impl;

import core.basesyntax.service.Writer;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterImplTest {
    private static Writer writer;

    @BeforeClass
    public static void setUp() {
        writer = new WriterImpl();
    }

    @Test
    public void write_validData_ok() {
        // expected
        String content = "testContent123  .. to add" + System.lineSeparator()
                + "second line of test writer" + System.lineSeparator()
                + "the last one is here";
        File expectedFile = new File("src/test/resources/writer_validDataExpected.csv");
        try {
            Files.write(expectedFile.toPath(), Collections.singleton(content));
        } catch (IOException e) {
            throw new RuntimeException("Can`t write data to file " + expectedFile.getName(), e);
        }
        List<String> expected;
        try {
            expected = Files.readAllLines(expectedFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Can`t read data from file " + expectedFile.getName(), e);
        }

        // actual
        File actualFile = writer.write(content, "src/test/resources/writer_validDataActual.csv");
        List<String> actual;
        try {
            actual = Files.readAllLines(actualFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Can`t read data from file " + actualFile.getName());
        }

        // comparison
        for (int i = 0; i < actual.size(); i++) {
            Assert.assertEquals(expected.get(i), actual.get(i));
        }
    }

    @Test(expected = RuntimeException.class)
    public void write_nullContent_notOk() {
        writer.write(null, "src/test/resources/writer_nullContent.csv");
    }

    @Test(expected = RuntimeException.class)
    public void write_nullFilePath_notOk() {
        writer.write("some_content", null);
    }

    @Test(expected = RuntimeException.class)
    public void write_unValidFilePath() {
        writer.write("hello", "unValid/path");
    }
}
