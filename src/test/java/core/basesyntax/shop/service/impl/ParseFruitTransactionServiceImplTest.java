package core.basesyntax.shop.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.shop.model.FruitTransaction;
import core.basesyntax.shop.model.OperationType;
import core.basesyntax.shop.service.ParseFruitTransactionService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ParseFruitTransactionServiceImplTest {
    private static ParseFruitTransactionService parseFruitTransaction;
    private static List<FruitTransaction> expectedList = new ArrayList<>();
    private static final OperationType OPERATION_TYPE_BALANCE = OperationType.BALANCE;
    private static final String BANANA_FRUIT_NAME = "banana";
    private static final int BANANA_FRUIT_QUANTITY = 100;
    private static final String APPLE_FRUIT_NAME = "apple";
    private static final int APPLE_FRUIT_QUANTITY = 50;
    private static final List<String> testData = new ArrayList<>();

    @BeforeAll
    static void beforeAll() {
        parseFruitTransaction = new ParseFruitTransactionServiceImpl();
        testData.add("type,fruit,quantity");
        testData.add(OPERATION_TYPE_BALANCE.getCode()
                + "," + BANANA_FRUIT_NAME + ","
                + BANANA_FRUIT_QUANTITY);
        testData.add(OPERATION_TYPE_BALANCE.getCode()
                + "," + APPLE_FRUIT_NAME + ","
                + APPLE_FRUIT_QUANTITY);

        expectedList.add(new FruitTransaction(
                OPERATION_TYPE_BALANCE, BANANA_FRUIT_NAME, BANANA_FRUIT_QUANTITY));
        expectedList.add(new FruitTransaction(
                OPERATION_TYPE_BALANCE, APPLE_FRUIT_NAME, APPLE_FRUIT_QUANTITY));
    }

    @Test
    void parseFruitTransactionValidData_Ok() {
        assertEquals(expectedList, parseFruitTransaction.getFruitOperations(testData));
    }

    @Test
    void parseFruitTransactionNotValidData_NotOk() {
        List<String> testData = new ArrayList<>(Arrays.asList(
                "type,fruit,quantity",
                "b,banana,100",
                "d,apple,50"));
        assertThrows(RuntimeException.class,
                () -> parseFruitTransaction.getFruitOperations(testData));
    }

}
