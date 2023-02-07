package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReaderServiceImplTest {
    private ReaderServiceImpl readerService;

    @Before
    public void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void read_fileWithCorrectData_ok() {
        int expectedListSize = 8;
        String inputPath = "src/test/resources/input-valid-data.csv";
        List<FruitTransaction> result = readerService.read(inputPath);
        Assert.assertEquals("Should read all line of data",expectedListSize, result.size());
    }

    @Test(expected = RuntimeException.class)
    public void read_fileWithIncorrectTitle_notOk() throws RuntimeException {
        String inputPath = "src/test/resources/input-data-with-invalid-column-name.csv";
        List<FruitTransaction> result = readerService.read(inputPath);
    }

    @Test(expected = RuntimeException.class)
    public void read_fileWithIncorrectOperation_notOk() throws RuntimeException {
        String inputPath = "src/test/resources/input-data-with-invalid-operator.csv";
        List<FruitTransaction> result = readerService.read(inputPath);
    }

    @Test(expected = RuntimeException.class)
    public void read_fileDoesNotExist_notOk() throws RuntimeException {
        String inputPath = "src/test/resources/does-not-exist.csv";
        List<FruitTransaction> result = readerService.read(inputPath);
    }
}
