package core.basesyntax.shop.service.impl;

import core.basesyntax.shop.service.Reader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ReaderFromCsvFileTest {
    private static final String CORRECT_FILEPATH
            = "src/test/resources/death_by_water.csv";
    private static final String EMPTY_FILE_FILEPATH
            = "src/test/resources/empty.csv";
    private static final String NON_EXISTING_FILEPATH
            = "src/test/resources/non_existing.csv";
    private static Reader reader;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        reader = new ReaderFromCsvFile();
    }

    @Test
    public void read_readFromFile_ok() {
        String actual = reader.read(CORRECT_FILEPATH);
        String expected = "";
        try {
            expected = Files.readString(Path.of(CORRECT_FILEPATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file " + CORRECT_FILEPATH, e);
        }
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void read_readFromEmptyFile_ok() {
        String actual = reader.read(EMPTY_FILE_FILEPATH);
        String expected = "";
        try {
            expected = Files.readString(Path.of(EMPTY_FILE_FILEPATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file " + EMPTY_FILE_FILEPATH, e);
        }
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void read_readFromNonExistingFile_notOk() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Can't read from file " + NON_EXISTING_FILEPATH);
        reader.read(NON_EXISTING_FILEPATH);
    }
}
