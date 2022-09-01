package core.basesyntax.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReaderServiceImplTest {
    private String filePath;
    private ReaderServiceImpl readerService;

    @Before
    public void setUp() {
        filePath = "src/test/resources/Input.csv";
        readerService = new ReaderServiceImpl();
    }

    @Test (expected = RuntimeException.class)
    public void readFileNotValidPath() {
        filePath = "src/test/resource/Inpute.csv";
        readerService.readFromFile(filePath);
    }

    @Test
    public void readFileValidPath() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,88");
        List<String> actual = readerService.readFromFile(filePath);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void readFileOk() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,88");
        List<String> actual = readerService.readFromFile(filePath);
        Assert.assertEquals(expected, actual);
    }
}
