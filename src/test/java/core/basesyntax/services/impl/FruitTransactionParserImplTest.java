package core.basesyntax.services.impl;

import static core.basesyntax.services.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.models.FruitTransaction;
import core.basesyntax.services.FruitTransactionParser;
import core.basesyntax.services.handlers.OperationHandler;
import core.basesyntax.services.handlers.impl.BalanceOperationHandler;
import core.basesyntax.services.handlers.impl.PurchaseOperationHandler;
import core.basesyntax.services.handlers.impl.ReturnOperationHandler;
import core.basesyntax.services.handlers.impl.SupplyOperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FruitTransactionParserImplTest {
    private static final FruitTransaction BALANCE_ORANGE_FRUITTRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, ORANGE, 100);
    private static final FruitTransaction SUPPLY_ORANGE_FRUITTRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, ORANGE, 5);
    private static final FruitTransaction PURCHASE_ORANGE_FRUITTRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, ORANGE, 25);
    private static final FruitTransaction RETURN_ORANGE_FRUITTRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.RETURN, ORANGE, 10);
    private static final FruitTransaction BALANCE_APPLE_FRUITTRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE, 200);
    private static final FruitTransaction SUPPLY_APPLE_FRUITTRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, APPLE, 10);
    private static final FruitTransaction PURCHASE_APPLE_FRUITTRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, APPLE, 50);
    private static final FruitTransaction RETURN_APPLE_FRUITTRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.RETURN, APPLE, 20);
    private static final FruitTransaction BALANCE_BANANA_FRUITTRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, BANANA, 300);
    private static final FruitTransaction SUPPLY_BANANA_FRUITTRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA, 15);
    private static final FruitTransaction PURCHASE_BANANA_FRUITTRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA, 75);
    private static final FruitTransaction RETURN_BANANA_FRUITTRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.RETURN, BANANA, 30);
    private static final int EXPECTED_QUANTITY_OF_ORANGE = 90;
    private static final int EXPECTED_QUANTITY_OF_APPLE = 180;
    private static final int EXPECTED_QUANTITY_OF_BANANA = 270;
    private static final List<FruitTransaction> fruitTransactionList =
            List.of(BALANCE_ORANGE_FRUITTRANSACTION, SUPPLY_ORANGE_FRUITTRANSACTION,
                    PURCHASE_ORANGE_FRUITTRANSACTION, RETURN_ORANGE_FRUITTRANSACTION,
                    BALANCE_APPLE_FRUITTRANSACTION, SUPPLY_APPLE_FRUITTRANSACTION,
                    PURCHASE_APPLE_FRUITTRANSACTION, RETURN_APPLE_FRUITTRANSACTION,
                    BALANCE_BANANA_FRUITTRANSACTION, SUPPLY_BANANA_FRUITTRANSACTION,
                    PURCHASE_BANANA_FRUITTRANSACTION, RETURN_BANANA_FRUITTRANSACTION);
    private static final Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap =
            Map.of(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(),
                    FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(),
                    FruitTransaction.Operation.RETURN, new ReturnOperationHandler(),
                    FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
    private static OperationStrategy operationStrategy;
    private static FruitTransactionParser fruitTransactionParserImpl;

    @BeforeAll
    static void initFruitTransactionParser() {
        fruitTransactionParserImpl = new FruitTransactionParserImpl();
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @AfterAll
    static void closeFruitTransactionParser() {
        fruitTransactionParserImpl = null;
    }

    @Test
    void runOperationsOverFruit_runOverGoodTransactions_Ok() {
        fruitTransactionParserImpl.runOperationsOverFruit(fruitTransactionList, operationStrategy);
        assertTrue(Storage.getFruits().containsKey(ORANGE));
        assertTrue(Storage.getFruits().containsKey(APPLE));
        assertTrue(Storage.getFruits().containsKey(BANANA));
        assertTrue(Storage.getFruits().containsValue(EXPECTED_QUANTITY_OF_ORANGE));
        assertTrue(Storage.getFruits().containsValue(EXPECTED_QUANTITY_OF_APPLE));
        assertTrue(Storage.getFruits().containsValue(EXPECTED_QUANTITY_OF_BANANA));
        assertEquals(EXPECTED_QUANTITY_OF_ORANGE, Storage.getFruits().get(ORANGE));
        assertEquals(EXPECTED_QUANTITY_OF_APPLE, Storage.getFruits().get(APPLE));
        assertEquals(EXPECTED_QUANTITY_OF_BANANA, Storage.getFruits().get(BANANA));
    }
}
