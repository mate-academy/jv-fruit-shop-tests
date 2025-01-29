package core.basesyntax.dataservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.storage.Storage;
import core.basesyntax.transactions.BalanceOperation;
import core.basesyntax.transactions.FruitTransaction;
import core.basesyntax.transactions.OperationHandler;
import core.basesyntax.transactions.OperationStrategy;
import core.basesyntax.transactions.OperationStrategyImpl;
import core.basesyntax.transactions.PurchaseOperation;
import core.basesyntax.transactions.ReturnOperation;
import core.basesyntax.transactions.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
        Storage.clearStorage();
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @Test
    void processing_SupplyOperationIsValid_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("banana");
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction.setQuantity(89);
        shopService.process(List.of(fruitTransaction));
        assertEquals(89, Storage.get("banana"));
    }

    @Test
    void processing_BalanceOperationIsValid_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setQuantity(70);
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("apple");
        shopService.process(List.of(fruitTransaction));
        assertEquals(70, Storage.get("apple"));
    }

    @Test
    void processing_BalanceOperationIsZero_NotOk() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setQuantity(0);
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("apple");
        assertThrows(IllegalArgumentException.class, () -> {
            shopService.process(List.of(fruitTransaction));
        });
    }

    @Test
    void processing_BalanceOperationIsLessThanZero_NotOk() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setQuantity(-200);
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("apple");
        assertThrows(IllegalArgumentException.class, () -> {
            shopService.process(List.of(fruitTransaction));
        });
    }

    @Test
    void processing_OperationsAreValid_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setQuantity(200);
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("pineapple");
        shopService.process(List.of(fruitTransaction));
        FruitTransaction fruitTransaction1 = new FruitTransaction();
        fruitTransaction1.setQuantity(150);
        fruitTransaction1.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction1.setFruit("pineapple");
        shopService.process(List.of(fruitTransaction1));
        FruitTransaction fruitTransaction2 = new FruitTransaction();
        fruitTransaction2.setQuantity(50);
        fruitTransaction2.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction2.setFruit("pineapple");
        shopService.process(List.of(fruitTransaction2));
    }

    @Test
    void processing_SupplyOperationIsInvalid_NotOk() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setQuantity(100);
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("pitch");
        shopService.process(List.of(fruitTransaction));
        FruitTransaction fruitTransaction1 = new FruitTransaction();
        fruitTransaction1.setQuantity(170);
        fruitTransaction1.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction1.setFruit("pitch");
        assertThrows(IllegalArgumentException.class, () -> {
            shopService.process(List.of(fruitTransaction1));
        });
    }
}
