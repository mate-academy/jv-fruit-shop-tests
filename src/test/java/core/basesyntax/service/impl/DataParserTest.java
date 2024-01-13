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
    private static final String OPERATION_TYPE = "b";
    private static final String FRUIT_TYPE = "banana";
    private static List<String> correctInput;
    private static List<String> incorrectOperationInput;
    private static List<String> incorrectQuantityInput;
    private static FruitTransaction expectedTransaction;
    private static DataParserImpl dataParser;

    @BeforeAll
    static void beforeAll() {
        correctInput = Arrays.asList("type,fruit,quantity", "b,banana,20");
        incorrectOperationInput = Arrays.asList("type,fruit,quantity", "null,banana,20");
        incorrectQuantityInput = Arrays.asList("type,fruit,quantity", "b,banana,null");
        dataParser = new DataParserImpl();
        expectedTransaction = new FruitTransaction(OPERATION_TYPE, FRUIT_TYPE, FRUIT_QUANTITY);
    }

    @Test
    void parse_correctInput_Ok() {
        List<FruitTransaction> transactions = dataParser.parse(correctInput);
        assertEquals(expectedTransaction, transactions.get(TEST_TRANSACTION_INDEX));
    }

    @Test
    void parse_incorrectOperationType_notOk() {
        assertThrows(RuntimeException.class, () -> dataParser.parse(incorrectOperationInput));
    }

    @Test
    void parse_incorrectQuantity_notOK() {
        assertThrows(RuntimeException.class, () -> dataParser.parse(incorrectQuantityInput));
    }
}
