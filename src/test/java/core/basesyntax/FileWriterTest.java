package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriter;
import core.basesyntax.service.impl.FileWriteServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterTest {
    private static FileWriter writer;

    @BeforeClass
    public static void before() {
        writer = new FileWriteServiceImpl();
    }

    @Test
    public void writeData_Ok() {
        String contentToWrite = "type,fruit,quantity"
                + System.lineSeparator() + "p,apple,20"
                + System.lineSeparator() + "s,banana,150";
        String outputFile = "src/test/resources/output.csv";
        writer.writeData(contentToWrite, outputFile);

        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(outputFile));
        } catch (IOException e) {
            throw new RuntimeException("Can't open " + outputFile);
        }
        List<String> expected = List.of(contentToWrite.split(System.lineSeparator()));
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void invalidFileName_Not_Ok() {
        String contentToWrite = "p,apple,20" + System.lineSeparator() + "s,banana,150";
        String outputFile = "/";
        writer.writeData(outputFile, contentToWrite);
    }
}
