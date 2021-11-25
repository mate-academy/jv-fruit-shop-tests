package core.basesyntax.service;

import core.basesyntax.service.impl.CsvFileReader;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class CsvFileReaderTest {
    private static final FileReader FILE_READER = new CsvFileReader();

    @Test
    public void readFromFile_Ok() {
        List<String> actual = FILE_READER.readFile("src/test/java/test.csv");
        List<String> expected = List.of("type,quantity", "banana,15");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void readFromEmptyFile_Ok() {
        List<String> actual = FILE_READER.readFile("src/test/java/empty.file.scv");
        List<String> expected = Collections.emptyList();
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
        public void invalidPath_notOk() {
        FILE_READER.readFile("");
    }
}
