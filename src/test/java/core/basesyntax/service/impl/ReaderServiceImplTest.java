package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String TEST_FILE = "src/test/resources/test.csv";
    private static final String TEST_TEXT = "carrot,25";
    private static ReaderService readerService;

    @BeforeClass
    public static void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readerService_readFromFile_ok() {
        File file = new File(TEST_FILE);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedWriter.write(TEST_TEXT);
        } catch (IOException e) {
            throw new RuntimeException("No such file at " + TEST_FILE, e);
        }
        String actual = readerService.readFromFile(TEST_FILE).get(0);
        Assert.assertEquals(TEST_TEXT, actual);
    }

    @Test(expected = NullPointerException.class)
    public void readerService_readFromNullFile_notOk() {
        readerService.readFromFile(null);
    }
}
