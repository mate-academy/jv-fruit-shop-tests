package core.basesyntax.shop.service.impl;

import core.basesyntax.shop.service.Reader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.Test;

public class ReaderFromCsvFileTest {
    private static final String FILEPATH = "src/test/resources//death_by_water.csv";

    @Test
    public void read_readFromFile_ok() {
        Reader reader = new ReaderFromCsvFile();
        String actual = reader.read(FILEPATH);
        String expected = "";
        try {
            expected = Files.readString(Path.of(FILEPATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file " + FILEPATH, e);
        }
        Assert.assertEquals(actual, expected);
    }
}
