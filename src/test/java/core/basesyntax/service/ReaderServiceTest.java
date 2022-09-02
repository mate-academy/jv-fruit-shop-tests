package core.basesyntax.service;

import core.basesyntax.impl.ReaderServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceTest {
    private static final String EXISTING_FILE_PATH = "src/test/resources/fruittest.csv";
    private static final String NON_EXISTING_FILE_PATH = "src/test/resources/nonexistingfile.csv";
    private static ReaderService readerService;

    @BeforeClass
    public static void beforeClass() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readFromFile_existingFile_Ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,10");
        expected.add("b,apple,20");
        List<String> actual = readerService.readFromFile(EXISTING_FILE_PATH);
        Assert.assertEquals(expected,actual);
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_nonExistingFile_NotOk() {
        readerService.readFromFile(NON_EXISTING_FILE_PATH);
    }
}
