package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParserService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionCsvParserServiceImplTest {
    private static final List<String> VALID_FORMAT_DATA =
            List.of("type,fruit,quantity", "b,banana,20");
    private static final List<String> INVALID_FORMAT_DATA =
            List.of("fruit,quantity,type,", "banana,20,b");
    private static final String NORMAL_OPERATION_TYPE = "b";
    private static final String NORMAL_FRUIT_NAME = "banana";
    private static final int NORMAL_FRUIT_QUANTITY = 20;
    private static TransactionParserService transactionParserService;

    @BeforeAll
    static void beforeAll() {
        transactionParserService = new TransactionCsvParserServiceImpl();
    }

    @Test
    void parse_nullData_notOk() {
        assertThrows(RuntimeException.class, () -> transactionParserService.parse(null));
    }

    @Test
    void parse_validData_ok() {
        List<FruitTransaction> actual = transactionParserService.parse(VALID_FORMAT_DATA);
        assertEquals(NORMAL_OPERATION_TYPE, actual.get(0).getOperation().getCode());
        assertEquals(NORMAL_FRUIT_NAME, actual.get(0).getFruit());
        assertEquals(NORMAL_FRUIT_QUANTITY, actual.get(0).getQuantity());
    }

    @Test
    void parse_invalidData_notOk() {
        assertThrows(RuntimeException.class,
                () -> transactionParserService.parse(INVALID_FORMAT_DATA));
    }
}
