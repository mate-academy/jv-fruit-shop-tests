package core.basesyntax.service.impl;

import core.basesyntax.exceptions.NullDataException;
import core.basesyntax.exceptions.NullFileException;
import core.basesyntax.exceptions.ReadDataException;
import core.basesyntax.service.FileWriterService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class CsvFileWriterTest {
    private static final List<String> DATA = List.of("test message1\n",
            "aA1!@:',&*\n", "test message2");
    private static final List<String> READ_DATA = List.of("test message1",
            "aA1!@:',&*", "test message2");
    private final File file = new File("src/test/resources/test_write_to.csv");
    private final FileWriterService fileWriter;

    public CsvFileWriterTest() {
        fileWriter = new CsvFileWriter();
    }

    @Test
    public void writeToFile_Ok() {
        fileWriter.writeToFile(file, DATA);
        try {
            Assert.assertEquals(READ_DATA, Files.readAllLines(file.toPath()));
        } catch (IOException e) {
            throw new ReadDataException("Can't read data from file " + file);
        }
    }

    @Test
    public void writeNothingToFile_Ok() {
        fileWriter.writeToFile(file, Collections.emptyList());
        try {
            Assert.assertEquals(Collections.emptyList(), Files.readAllLines(file.toPath()));
        } catch (IOException e) {
            throw new ReadDataException("Can't read data from file " + file);
        }
    }

    @Test (expected = NullFileException.class)
    public void writeInNullFile_NotOk() {
        fileWriter.writeToFile(null, DATA);
    }

    @Test (expected = NullDataException.class)
    public void writeNullDataToFile_NotOk() {
        fileWriter.writeToFile(file, null);
    }
}
