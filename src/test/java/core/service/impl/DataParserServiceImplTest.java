package core.service.impl;

import static org.junit.Assert.assertEquals;

import core.model.FruitTransaction;
import core.service.DataParserService;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataParserServiceImplTest {
    private static final int INDEX_FIRST_LINE = 0;
    private static final List<String> VALID_DATA = List.of("type,fruit,quantity", "p,banana,13");
    private static final List<String> INVALID_DATA = List.of("type,fruit", "p,banana");
    private static DataParserService dataParserService;

    @BeforeClass
    public static void init() {
        dataParserService = new DataParserServiceImpl();
    }

    @Test
    public void parseList_Ok() {
        String fruit = "banana";
        int quantity = 13;
        FruitTransaction.Operation operation = FruitTransaction.Operation.PURCHASE;
        List<FruitTransaction> actual = dataParserService.parseList(VALID_DATA);
        assertEquals(fruit, actual.get(INDEX_FIRST_LINE).getFruit());
        assertEquals(quantity, actual.get(INDEX_FIRST_LINE).getQuantity());
        assertEquals(operation, actual.get(INDEX_FIRST_LINE).getOperation());
    }

    @Test(expected = RuntimeException.class)
    public void parseList_invalidData_notOk() {
        dataParserService.parseList(INVALID_DATA);
    }

    @Test(expected = NullPointerException.class)
    public void parseList_nullData_notOk() {
        dataParserService.parseList(null);
    }
}
