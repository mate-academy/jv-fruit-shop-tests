package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.BalanceOperation;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.PurchaseOperation;
import core.basesyntax.operation.ReturnOperation;
import core.basesyntax.operation.SupplyOperation;
import core.basesyntax.service.impl.OperationStrategy;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private Map<FruitTransaction.Operation, OperationHandler> handlers;
    private ShopServiceImpl shopService;
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        handlers = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(),
                FruitTransaction.Operation.RETURN, new ReturnOperation(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation()
        );
        operationStrategy = new OperationStrategyImpl(handlers);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @AfterEach
    void cleaner() {
        Storage.getInventory().clear();
    }

    @Test
    void service_ValidValue_Ok() {
        FruitTransaction firstTransaction = new FruitTransaction();
        firstTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        firstTransaction.setFruit("banana");
        firstTransaction.setQuantity(50);

        FruitTransaction secondTransaction = new FruitTransaction();
        secondTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        secondTransaction.setFruit("banana");
        firstTransaction.setQuantity(25);

        FruitTransaction thirdTransaction = new FruitTransaction();
        thirdTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        thirdTransaction.setFruit("banana");
        thirdTransaction.setQuantity(5);

        FruitTransaction fourthTransaction = new FruitTransaction();
        fourthTransaction.setOperation(FruitTransaction.Operation.RETURN);
        fourthTransaction.setFruit("banana");
        thirdTransaction.setQuantity(100);

        List<FruitTransaction> actualContent = List.of(
                firstTransaction,
                secondTransaction,
                thirdTransaction,
                fourthTransaction
        );
        shopService.process(actualContent);

        Map<String, Integer> expectedContent = Map.of(
                "banana", 125
        );

        Map<String, Integer> actualInventory = Storage.getInventory();
        Assertions.assertEquals(expectedContent, actualInventory);
    }

    @Test
    void service_EmptyStorage_Ok() {
        List<FruitTransaction> emptyFruitTransaction = Collections.emptyList();
        shopService.process(emptyFruitTransaction);
        Map<String, Integer> emptyStorage = Storage.getInventory();
        Assertions.assertTrue(emptyStorage.isEmpty());
    }

    @Test
    void service_NullValue_NotOk() {
        FruitTransaction firstTransaction = new FruitTransaction();
        firstTransaction.setOperation(null);
        firstTransaction.setFruit(null);
        firstTransaction.setQuantity(100);

        FruitTransaction secondTransaction = new FruitTransaction();
        secondTransaction.setOperation(null);
        secondTransaction.setFruit(null);
        firstTransaction.setQuantity(75);

        List<FruitTransaction> negativeValue = List.of(
                firstTransaction,
                secondTransaction
        );
        Assertions.assertThrows(RuntimeException.class, () -> shopService.process(negativeValue));
    }
}
