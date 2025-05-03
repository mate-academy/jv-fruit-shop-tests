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
    private static final FileWriter fileWriter = new CsvFileWriter();

    @Test
    public void write_writeToFile_Ok() {
        String expectedMsg = "some info";
        List<String> expected = new ArrayList<>();
        List<String> actual = null;
        expected.add("some info");
        fileWriter.write("src/test/resources/output.csv", expectedMsg);
        try {
            actual = Files.readAllLines(Path.of("src/test/resources/output.csv"));
        } catch (IOException e) {
            throw new RuntimeException("Can't read info from file, " + e);
        }
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void write_writeToFileWithInvalidPath_notOk() {
        fileWriter.write("", "");
    }
}
