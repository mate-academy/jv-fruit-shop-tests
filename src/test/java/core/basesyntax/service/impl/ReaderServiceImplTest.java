package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReaderService;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String TEST_FILE = "src/test/resources/test.csv";
    private static final String TEST_TEXT = "carrot,25";
    private static ReaderService readerService;

    @Before
    public void setUp() throws Exception {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readerService_readFromFile_ok() {
        File file = new File(TEST_FILE);
        BufferedWriter bufferedWriter;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(TEST_TEXT);
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException("No such file at " + TEST_FILE, e);
        }
        String actual = readerService.readFromFile(TEST_FILE).get(0);
        Assert.assertEquals(TEST_TEXT, actual);
    }

    @Test(expected = NullPointerException.class)
    public void readerService_readFromFile_NotOk() {
        readerService.readFromFile(null);
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }
}
