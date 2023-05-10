package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitOperationStrategy;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.impl.BalanceOperationHandler;
import core.basesyntax.service.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.service.strategy.impl.ReturnOperationHandler;
import core.basesyntax.service.strategy.impl.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class FruitOperationStrategyImplTest {
    private final Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap =
            new HashMap<>();
    private final FruitOperationStrategy fruitOperationStrategy =
            new FruitOperationStrategyImpl(operationHandlerMap);

    @Test
    void put_Balance_Ok() {
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler());

        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(100);

        fruitOperationStrategy.put(fruitTransaction).transaction(fruitTransaction);
        assertEquals(100, Storage.fruitBalance.get("apple"));
    }

    @Test
    void put_Purchase_Ok() {
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        Storage.fruitBalance.put("apple", 150);

        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(100);

        fruitOperationStrategy.put(fruitTransaction).transaction(fruitTransaction);
        assertEquals(50, Storage.fruitBalance.get("apple"));
    }

    @Test
    void put_Return_Ok() {
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler());
        Storage.fruitBalance.put("apple", 30);

        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(80);

        fruitOperationStrategy.put(fruitTransaction).transaction(fruitTransaction);
        assertEquals(110, Storage.fruitBalance.get("apple"));
    }

    @Test
    void put_Supply_Ok() {
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler());
        Storage.fruitBalance.put("apple", 150);

        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(50);

        fruitOperationStrategy.put(fruitTransaction).transaction(fruitTransaction);
        assertEquals(200, Storage.fruitBalance.get("apple"));
    }

    @AfterEach
    void tearDown() {
        Storage.fruitBalance.clear();
    }
}
