package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.operation.BalanceOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.service.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitShopServiceImplTest {
    private static final String FRUIT = "apple";
    private static final int VALID_QUANTITY = 20;
    private static final List<FruitTransaction> FRUIT_TRANSACTIONS = List.of(
            createFruitTransaction(FruitTransaction.Operation.BALANCE, FRUIT, VALID_QUANTITY)
    );
    private static FruitShopService fruitShopService;
    private static OperationStrategy operationStrategy;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;

    @BeforeEach
    void setUp() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        fruitShopService = new FruitShopServiceImpl(operationStrategy);
        Storage.fruits.put(FRUIT, VALID_QUANTITY);
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void processingOperations_validTransactions_Ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setQuantity(Storage.fruits.get(FRUIT));
        fruitShopService.processingOperations(FRUIT_TRANSACTIONS);
        FruitTransaction actualTransaction = new FruitTransaction();
        actualTransaction.setQuantity(Storage.fruits.get(FRUIT));
        assertEquals(transaction, actualTransaction);

    }

    private static FruitTransaction createFruitTransaction(FruitTransaction.Operation operation,
                                                    String fruit,
                                                    int quantity) {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(operation);
        transaction.setFruit(fruit);
        transaction.setQuantity(quantity);
        return transaction;
    }
}
