package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParser;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvTransactionParserImplTest {
    private static final FruitTransaction.Operation NORMAL_OPERATION =
            FruitTransaction.Operation.BALANCE;
    private static final String NORMAL_FRUIT = "banana";
    private static final int NORMAL_QUANTITY = 25;
    private static final List<String> NORMAL_OPERATION_FORMAT =
            List.of("type,fruit,quantity", "b,banana,25");
    private static final List<String> WRONG_DATA_FORMAT =
            List.of("a", "b", "banana");
    private static final List<String> WRONG_OPERATION_FORMAT =
            List.of("type,fruit,quantity", "c,banana,25");
    private static final List<String> WRONG_QUANTITY_FORMAT =
            List.of("type,fruit,quantity", "c,banana,-25");
    private static TransactionParser parser;

    @BeforeAll
    static void beforeAll() {
        parser = new CsvTransactionParserImpl();
    }

    @Test
    void parseNullData_NotOk() {
        Assert.assertThrows(RuntimeException.class,
                () -> parser.fruitTransaction(null));
    }

    @Test
    void parseWrongDataFormat_NotOk() {
        Assert.assertThrows(RuntimeException.class,
                () -> parser.fruitTransaction(WRONG_DATA_FORMAT));
    }

    @Test
    void parseWrongOperationFormat_NotOk() {
        Assert.assertThrows(RuntimeException.class,
                () -> parser.fruitTransaction(WRONG_OPERATION_FORMAT));
    }

    @Test
    void parseWrongQuantityFormat_NotOk() {
        Assert.assertThrows(RuntimeException.class,
                () -> parser.fruitTransaction(WRONG_QUANTITY_FORMAT));
    }

    @Test
    void returnListIsNotEmpty_Ok() {
        Assertions.assertFalse(parser.fruitTransaction(NORMAL_OPERATION_FORMAT).isEmpty());
    }

    @Test
    void parseData_Ok() {
        List<FruitTransaction> transactions = parser.fruitTransaction(NORMAL_OPERATION_FORMAT);
        FruitTransaction expected =
                new FruitTransaction(NORMAL_OPERATION, NORMAL_FRUIT, NORMAL_QUANTITY);
        FruitTransaction actual = transactions.get(0);
        Assert.assertEquals(expected, actual);
    }
}
