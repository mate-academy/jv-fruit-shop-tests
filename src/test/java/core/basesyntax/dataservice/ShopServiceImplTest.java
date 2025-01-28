package core.basesyntax.dataservice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.storage.Storage;
import core.basesyntax.transactions.BalanceOperation;
import core.basesyntax.transactions.FruitTransaction;
import core.basesyntax.transactions.OperationHandler;
import core.basesyntax.transactions.OperationStrategy;
import core.basesyntax.transactions.OperationStrategyImpl;
import core.basesyntax.transactions.PurchaseOperation;
import core.basesyntax.transactions.ReturnOperation;
import core.basesyntax.transactions.SupplyOperation;
import org.junit.jupiter.api.Assertions;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private ShopService shopService;
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy);
        fruitTransaction = new FruitTransaction();
    }

    @Test
    void processing_SupplyOperationIsValid() {
        assertEquals(0, Storage.get("banana"));
        fruitTransaction.setFruit("banana");
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction.setQuantity(89);
        shopService.process(List.of(fruitTransaction));
        assertEquals(89, Storage.get("banana"));
    }

    @Test
    void processing_BalanceOperationIsValid() {
        assertEquals(0, Storage.get("apple"));
        fruitTransaction.setQuantity(70);
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("apple");
        shopService.process(List.of(fruitTransaction));
        assertEquals(70, Storage.get("apple"));
    }

    @Test
    void processing_BalanceOperationIsZero_NotOk() {
        assertEquals(0, Storage.get("apple"));
        fruitTransaction.setQuantity(0);
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("apple");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            shopService.process(List.of(fruitTransaction));
        });
    }

    @Test
    void processing_BalanceOperationIsLessThanZero_NotOk() {
        assertEquals(0, Storage.get("apple"));
        fruitTransaction.setQuantity(-200);
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("apple");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            shopService.process(List.of(fruitTransaction));
        });
    }

    @Test
    void processing_OperationsAreValid_Ok() {
        assertEquals(0, Storage.get("pineapple"));
        fruitTransaction.setQuantity(200);
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("pineapple");
        shopService.process(List.of(fruitTransaction));
        assertEquals(200,Storage.get("pineapple"));
        fruitTransaction.setQuantity(150);
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("pineapple");
        shopService.process(List.of(fruitTransaction));
        assertEquals(50, Storage.get("pineapple"));
        fruitTransaction.setQuantity(50);
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction.setFruit("pineapple");
        shopService.process(List.of(fruitTransaction));
        assertEquals(100, Storage.get("pineapple"));
    }

    @Test
    void processing_SupplyOperationIsInvalid_NotOk() {
        assertEquals(0, Storage.get("pitch"));
        fruitTransaction.setQuantity(100);
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("pitch");
        shopService.process(List.of(fruitTransaction));
        assertEquals(100,Storage.get("pitch"));
        fruitTransaction.setQuantity(170);
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("pitch");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            shopService.process(List.of(fruitTransaction));
        });
    }
}