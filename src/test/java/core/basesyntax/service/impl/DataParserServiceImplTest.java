package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataParserService;
import core.basesyntax.service.FileReadService;
import java.util.List;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DataParserServiceImplTest {
    private static final String INPUT_PATH = "src/test/resources/file_read_service_test.csv";
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();
    private final FileReadService fileReadService = new FileReadServiceImpl();
    private final DataParserService dataParserService = new DataParserServiceImpl();

    @Test
    public void dataParserService_ok() {
        String data = fileReadService.readFromFile(INPUT_PATH);
        List<FruitTransaction> transactions = dataParserService.getTransactions(data);
        int actualSize = transactions.size();
        int expected = 2;
        Assert.assertEquals(expected, actualSize);
    }

    @Test
    public void dataParserService_notOk() {
        String data = fileReadService.readFromFile("src/test/resources/invalid_data_test.csv");
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("Can`t find operation");
        dataParserService.getTransactions(data);
    }
}
