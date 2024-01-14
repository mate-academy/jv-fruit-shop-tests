package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataParserTest {
    private static final int TEST_TRANSACTION_INDEX = 0;
    private static final int FRUIT_QUANTITY = 20;
    private static final String FRUIT_TYPE = "banana";
    private static final List<String> CORRECT_INPUT
            = Arrays.asList("type,fruit,quantity", "b,banana,20");
    private static final List<String> INCORRECT_OPERATION_INPUT
            = Arrays.asList("type,fruit,quantity", "null,banana,20");
    private static final List<String> INCORRECT_QUANTITY_INPUT
            = Arrays.asList("type,fruit,quantity", "b,banana,null");
    private static FruitTransaction expectedTransaction;
    private static DataParserImpl dataParser;

    @BeforeAll
    static void beforeAll() {
        dataParser = new DataParserImpl();
        expectedTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE.getCode(),
                FRUIT_TYPE, FRUIT_QUANTITY);
    }

    @Test
    void parse_correctInput_Ok() {
        List<FruitTransaction> transactions = dataParser.parse(CORRECT_INPUT);
        assertEquals(expectedTransaction, transactions.get(TEST_TRANSACTION_INDEX));
    }

    @Test
    void parse_incorrectOperationType_notOk() {
        assertThrows(RuntimeException.class, () -> dataParser.parse(INCORRECT_OPERATION_INPUT));
    }

    @Test
    void parse_incorrectQuantity_notOK() {
        assertThrows(RuntimeException.class, () -> dataParser.parse(INCORRECT_QUANTITY_INPUT));
    }
}
