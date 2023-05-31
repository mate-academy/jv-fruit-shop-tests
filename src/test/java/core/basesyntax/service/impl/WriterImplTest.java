package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.Writer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterImplTest {
    private static Writer writer;
    private static final String REPORT = "apple,12";
    private static final String PATH = "src/test/resources/newFIle.csv";

    @BeforeClass
    public static void beforeClass() {
        writer = new WriterImpl();
    }

    @Test
    public void writerIsOk() {
        writer.write(REPORT, PATH);
        String actual = read();
        assertEquals(actual, REPORT);
    }

    private String read() {
        try {
            return String.join("", Files.readAllLines(Path.of(PATH)));
        } catch (IOException e) {
            throw new RuntimeException("Can't read this file", e);
        }
    }
}
