package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.TransactionParser;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionParserImplTest {
    private static TransactionParser transactionParser;

    @BeforeAll
    static void beforeAll() {
        transactionParser = new TransactionParserImpl();
    }

    @Test
    void parseCorrectData_Ok() {
        List<String> inputData = List.of("type,fruit,quantity",
                "b,banana,20",
                "r,apple,100",
                "s,banana,100",
                "p,banana,13");
        List<FruitTransaction> expectedData = Arrays.asList(
                createFruitTransaction(Operation.BALANCE, "banana", 20),
                createFruitTransaction(Operation.RETURN, "apple", 100),
                createFruitTransaction(Operation.SUPPLY, "banana", 100),
                createFruitTransaction(Operation.PURCHASE, "banana", 13));
        List<FruitTransaction> actualData = transactionParser.parseData(inputData);
        assertEquals(expectedData, actualData);
    }

    @Test
    void parseEmptyList_Ok() {
        List<String> inputData = Collections.emptyList();
        List<FruitTransaction> actualData = transactionParser.parseData(inputData);
        assertEquals(0, actualData.size());
    }

    @Test
    void parseIncorrectData_NotOk() {
        List<String> inputData = List.of("type,fruit,quantity",
                "b,banana,b",
                "1,1,100",
                "apple,banana,100",
                "p,banana,13");
        assertThrows(IllegalArgumentException.class, () -> transactionParser.parseData(inputData));
    }

    private FruitTransaction createFruitTransaction(
            Operation operation, String fruit, int quantity) {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(operation);
        transaction.setFruit(fruit);
        transaction.setQuantity(quantity);
        return transaction;
    }
}
