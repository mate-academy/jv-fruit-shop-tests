package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ParserServiceImplTest {
    private static ParserServiceImpl parserImpl;
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final String ORANGE = "orange";
    private static final String TITLE = "type,fruit,quantity";
    private static final List<String> VALUE_LIST = List.of(TITLE,
            "b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,109",
            "p,banana,155",
            "s,banana,50");

    @BeforeAll
    static void beforeAll() {
        parserImpl = new ParserServiceImpl();
    }

    @Test
    void parseRecords_ValidReport_Ok() {
        List<FruitTransaction> expected = List.of(
                createFruitTransaction(FruitTransaction.Operation.BALANCE, BANANA, 20),
                createFruitTransaction(FruitTransaction.Operation.BALANCE, APPLE, 100),
                createFruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA, 100),
                createFruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA, 13),
                createFruitTransaction(FruitTransaction.Operation.RETURN, APPLE, 10),
                createFruitTransaction(FruitTransaction.Operation.PURCHASE, APPLE, 109),
                createFruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA, 155),
                createFruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA, 50)
        );
        List<FruitTransaction> actual = parserImpl.parseRecords(VALUE_LIST);
        assertIterableEquals(expected, actual);
    }

    @Test
    void parseRecords_ValidInput_Ok() {
        List<String> validList = Arrays.asList(TITLE,
                "b,apple,5",
                "s,banana,10",
                "p,orange,3"
        );
        List<FruitTransaction> expectedTransactions = new ArrayList<>();
        expectedTransactions.add(createFruitTransaction(FruitTransaction.Operation
                .BALANCE, APPLE, 5));
        expectedTransactions.add(createFruitTransaction(FruitTransaction.Operation
                .SUPPLY, BANANA, 10));
        expectedTransactions.add(createFruitTransaction(FruitTransaction.Operation
                .PURCHASE, ORANGE, 3));
        List<FruitTransaction> actualTransactions = parserImpl.parseRecords(validList);
        Assertions.assertEquals(expectedTransactions, actualTransactions);
    }

    @Test
    void parseRecords_InvalidInput_NotOk() {
        List<String> invalidList = Arrays.asList(TITLE,
                "f,Apple,5",
                "s,Banana,10");
        assertThrows(RuntimeException.class, () -> parserImpl.parseRecords(invalidList));
    }

    @Test
    void parseRecords_EmptyList_Ok() {
        List<String> empty = new ArrayList<>();
        List<FruitTransaction> expected = new ArrayList<>();
        List<FruitTransaction> actual = parserImpl.parseRecords(empty);
        assertIterableEquals(expected, actual);
    }

    @Test
    void parseRecords_MissingFieldsInRow_NotOk() {
        List<String> invalidList = Arrays.asList(TITLE,
                "b,banana",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple",
                "p,banana,155",
                "s,banana,50");
        assertThrows(IllegalArgumentException.class, () -> parserImpl.parseRecords(invalidList));
    }

    private FruitTransaction createFruitTransaction(
            FruitTransaction.Operation operation, String fruit, int quantity) {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(operation);
        transaction.setFruit(fruit);
        transaction.setQuantity(quantity);
        return transaction;
    }
}
