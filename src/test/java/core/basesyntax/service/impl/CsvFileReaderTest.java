package core.basesyntax.service.impl;

import core.basesyntax.exceptions.NullFileException;
import core.basesyntax.exceptions.ReadDataException;
import core.basesyntax.service.FileReaderService;
import java.io.File;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class CsvFileReaderTest {
    private static final List<String> TEST_DATA = List.of("test message1",
            "aA1!@:',&*", "test message2");
    private final File file = new File("src/test/resources/test.csv");
    private final File emptyFile = new File("src/test/resources/empty.csv");
    private final File nonExistentFile = new File("src/test/resources/nonExistent.csv");
    private final FileReaderService fileReader;

    public CsvFileReaderTest() {
        fileReader = new CsvFileReader();
    }

    @Test
    public void readFromFile_validFile_Ok() {
        Assert.assertEquals(TEST_DATA, fileReader.readFromFile(file));
    }

    @Test
    public void readFromFile_emptyFile_Ok() {
        Assert.assertEquals(Collections.emptyList(), fileReader.readFromFile(emptyFile));
    }

    @Test (expected = NullFileException.class)
    public void readFromFile_NullFile_NotOk() {
        fileReader.readFromFile(null);
    }

    @Test (expected = ReadDataException.class)
    public void readFromFile_nonExistentFile_NotOk() {
        fileReader.readFromFile(nonExistentFile);
    }
}
