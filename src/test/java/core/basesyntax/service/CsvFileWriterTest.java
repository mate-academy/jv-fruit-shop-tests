package core.basesyntax.service;

import core.basesyntax.service.impl.CsvFileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class CsvFileWriterTest {
    private static final FileWriter FILE_WRITER = new CsvFileWriter();

    @Test
    public void writeToFile_Ok() {
        String expectedMsg = "some info";
        List<String> expected = new ArrayList<>();
        List<String> actual = null;
        expected.add("some info");
        FILE_WRITER.write("src/test/java/output.csv", expectedMsg);
        try {
            actual = Files.readAllLines(Path.of("src/test/java/output.csv"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void invalidPath_notOk() {
        FILE_WRITER.write("", "");
    }
}
