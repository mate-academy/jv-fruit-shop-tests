package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static String filePath;
    private static ReaderService readerService;

    @BeforeClass
    public static void beforeClass() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void read_validFile_Ok() {
        filePath = "src/test/resources/reader_test_file.csv";
        List<String> expected = Arrays.asList("Hello,",
                "I am here to test Reader!",
                "Best regards,",
                "Test File");
        List<String> actual = readerService.readFromFile(filePath);
        Assert.assertTrue("Wrong data",
                actual.equals(expected));
    }

    @Test
    public void read_emptyFile_Ok() {
        filePath = "src/test/resources/empty_file.csv";
        List<String> actual = readerService.readFromFile(filePath);
        Assert.assertTrue("Wrong data", actual.isEmpty());
    }

    @Test (expected = RuntimeException.class)
    public void read_notExistingFile_NotOk() {
        filePath = "src/test/resources/non_existent.csv";
        readerService.readFromFile(filePath);
    }
}
