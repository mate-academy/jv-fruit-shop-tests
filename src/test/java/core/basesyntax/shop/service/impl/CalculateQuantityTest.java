package core.basesyntax.shop.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import core.basesyntax.shop.db.Storage;
import core.basesyntax.shop.handler.BalanceHandler;
import core.basesyntax.shop.handler.OperationHandler;
import core.basesyntax.shop.handler.PurchaseHandler;
import core.basesyntax.shop.handler.ReturnHandler;
import core.basesyntax.shop.handler.SupplyHandler;
import core.basesyntax.shop.handler.strategy.OperationStrategyImpl;
import core.basesyntax.shop.model.FruitTransaction;
import core.basesyntax.shop.model.OperationType;
import core.basesyntax.shop.service.CalculateQuantityService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalculateQuantityTest {
    private static final String FRUIT_NAME = "banana";
    private static final int FRUIT_QUANTITY = 10;
    private static final int ADD_QUANTITY = 15;
    private static final int EXPECTED_QUANTITY = 25;
    private static final int INVALID_QUANTITY = 10;
    private static CalculateQuantityService calculateQuantity;
    private static Map<OperationType, OperationHandler> operationStrategyMap;
    private final List<FruitTransaction> fruitTransactionList = new ArrayList<>();

    @BeforeAll
    public static void beforeAll() {
        operationStrategyMap = new HashMap<>();
        operationStrategyMap.put(OperationType.BALANCE, new BalanceHandler());
        operationStrategyMap.put(OperationType.PURCHASE, new PurchaseHandler());
        operationStrategyMap.put(OperationType.SUPPLY, new SupplyHandler());
        operationStrategyMap.put(OperationType.RETURN, new ReturnHandler());
        calculateQuantity = new CalculateQuantityServiceImpl(
                new OperationStrategyImpl(operationStrategyMap));
    }

    @BeforeEach
    public void setUp() {
        fruitTransactionList.add(new FruitTransaction(
                OperationType.BALANCE, FRUIT_NAME, FRUIT_QUANTITY));
        fruitTransactionList.add(new FruitTransaction(
                OperationType.SUPPLY, FRUIT_NAME, ADD_QUANTITY));
    }

    @AfterEach
    public void afterEach() {
        Storage.fruits.clear();
    }

    @Test
    public void calculateQuantity_ok() {
        calculateQuantity.calculate(fruitTransactionList);
        int actual = Storage.fruits.get(FRUIT_NAME);
        assertEquals(EXPECTED_QUANTITY, actual);
    }

    @Test
    public void calculateInvalidQuantity_notOk() {
        calculateQuantity.calculate(fruitTransactionList);
        int actual = Storage.fruits.get(FRUIT_NAME);
        assertNotEquals(INVALID_QUANTITY, actual);
    }

}
