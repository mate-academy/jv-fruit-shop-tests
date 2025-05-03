package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionParserImplTest {
    private static TransactionParser transactionParser;
    private static List<String> data;
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;

    @BeforeAll
    static void beforeAll() {
        transactionParser = new TransactionParserImpl();
    }

    @BeforeEach
    void setUp() {
        data = new ArrayList<>();
        data.add("type,fruit,quantity");
        data.add("b,apple,45");
        data.add("s,apple,25");
        data.add("r,apple,5");
        data.add("p,apple,15");
    }

    @Test
    void getTransactions_nullData_notOk() {
        List<String> data = null;
        Assertions.assertThrows(RuntimeException.class,
                () -> transactionParser.getTransactions(data),
                "If Data is Null should be Exception");
    }

    @Test
    void getTransactions_data_Ok() {
        List<FruitTransaction> transactions = transactionParser.getTransactions(data);
        for (int i = 0; i < data.size(); i++) {
            String[] splitString = data.get(i).split(",");
            String expected;
            switch (splitString[OPERATION_INDEX]) {
                case "b" : {
                    expected = FruitTransaction.Operation.BALANCE.getCode();
                    break;
                }
                case "r" : {
                    expected = FruitTransaction.Operation.RETURN.getCode();
                    break;
                }
                case "p" : {
                    expected = FruitTransaction.Operation.PURCHASE.getCode();
                    break;
                }
                default : {
                    expected = FruitTransaction.Operation.SUPPLY.getCode();
                }
            }
            Assertions.assertEquals(expected,transactions.get(i).getOperation().getCode(),
                    "Data in List not equals expected");
            String expectedString = splitString[FRUIT_INDEX];
            Assertions.assertEquals(expectedString,transactions.get(i).getFruit(),
                    "Data in List not equals expected");
            int expectedQuantity = Integer.parseInt(splitString[QUANTITY_INDEX]);
            Assertions.assertEquals(expectedQuantity,transactions.get(i).getQuantity(),
                    "Data in List not equals expected");
        }
    }
}
