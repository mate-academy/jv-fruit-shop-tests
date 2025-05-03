package core.basesyntax.service;

import core.basesyntax.service.impl.CsvFileReader;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class CsvFileReaderTest {
    private static final FileReader fileReader = new CsvFileReader();

    @Test
    public void readFile_readFromValidFile_Ok() {
        List<String> actual = fileReader.readFile("src/test/resources/test.csv");
        List<String> expected = List.of("type,quantity", "banana,15");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void readFile_readFromEmptyFile_Ok() {
        List<String> actual = fileReader.readFile("src/test/resources/empty.file.scv");
        List<String> expected = Collections.emptyList();
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
        public void readFile_readFromInvalidFilePath_notOk() {
        fileReader.readFile("");
    }
}
