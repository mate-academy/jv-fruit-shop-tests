package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataParserService;
import core.basesyntax.service.FileReadService;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DataParserServiceImplTest {
    private static FileReadService fileReadService;
    private static DataParserService dataParserService;
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        dataParserService = new DataParserServiceImpl();
        fileReadService = new FileReadServiceImpl();
    }

    @Test
    public void dataParserService_validData_ok() {
        String data = fileReadService.readFromFile("src/test/resources/file_read_service_test.csv");
        List<FruitTransaction> transactions = dataParserService.getTransactions(data);
        int actualSize = transactions.size();
        int expected = 2;
        Assert.assertEquals(expected, actualSize);
    }

    @Test
    public void dataParserService_invalidOperation_notOk() {
        String data = fileReadService.readFromFile("src/test/resources/invalid_data_test.csv");
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("Can`t find operation");
        dataParserService.getTransactions(data);
    }
}
