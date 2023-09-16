package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterTest {
    private static Writer writer;

    @BeforeClass
    public static void before() {
        writer = new WriterImpl();
    }

    @Test
    public void writeToFile_Ok() {
        String contentToWrite = "What kind of tea is hard to swallow?\nReality.";
        String outputFile = "src/test/resources/output.txt";
        writer.writeToFile(outputFile, contentToWrite);

        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(outputFile));
        } catch (IOException e) {
            throw new RuntimeException("Can't open " + outputFile);
        }
        List<String> expected = List.of(contentToWrite.split("\n"));
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void invalidFileName_Not_Ok() {
        String contentToWrite = "What kind of tea is hard to swallow?\nReality.";
        String outputFile = "/";
        writer.writeToFile(outputFile, contentToWrite);
    }
}
