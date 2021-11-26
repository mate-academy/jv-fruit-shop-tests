package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.ReaderFromFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderFromFileCsvImplTest {
    private static final String DEFAULT_PATH = "src/test/resources/";
    private static ReaderFromFile reader;

    @BeforeClass
    public static void beforeClass() {
        reader = new ReaderFromFileCsvImpl();
    }

    @Test
    public void read_fileExists_OK() {
        String fileNameExists = "storeGood.csv";
        File fileExists = new File(DEFAULT_PATH + fileNameExists);
        List<String> expected = reader.read(fileExists);
        List<String> actual;
        try {
            actual = Files.readAllLines(fileExists.toPath());
        } catch (IOException e) {
            fail("Test couldn't find the file!!!");
            return;
        }
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void read_fileNotExists_notOk() {
        String fileNameNotExists = "missing.csv";
        File fileNotExists = new File(DEFAULT_PATH + fileNameNotExists);
        reader.read(fileNotExists);
    }

}
