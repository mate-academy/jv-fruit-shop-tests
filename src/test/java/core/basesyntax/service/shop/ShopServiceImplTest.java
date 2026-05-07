package core.basesyntax.service.shop;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.BalanceOperation;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.service.strategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static ShopService shopService;
    private static OperationStrategy operationStrategy;

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlersMap = new HashMap<>();
        operationHandlersMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationStrategy = new OperationStrategyImpl(operationHandlersMap);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @Test
    void process_validTransactionList_ok() {
        Storage.fruits.put("apple", 10);
        Storage.fruits.put("banana", 50);
        FruitTransaction bananaBalanceTransaction = new FruitTransaction();
        bananaBalanceTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        bananaBalanceTransaction.setFruit("banana");
        bananaBalanceTransaction.setQuantity(20);
        FruitTransaction mangoBalanceTransaction = new FruitTransaction();
        mangoBalanceTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        mangoBalanceTransaction.setFruit("mango");
        mangoBalanceTransaction.setQuantity(5);
        List<FruitTransaction> transactions = List.of(
                bananaBalanceTransaction, mangoBalanceTransaction
        );
        int expectedAppleQuantity = 10;
        int expectedBananaQuantity = 20;
        int expectedMangoQuantity = 5;
        int expectedStorageSize = 3;
        shopService.process(transactions);
        assertAll(
                () -> assertEquals(expectedAppleQuantity, Storage.fruits.get("apple")),
                () -> assertEquals(expectedBananaQuantity, Storage.fruits.get("banana")),
                () -> assertEquals(expectedMangoQuantity, Storage.fruits.get("mango")),
                () -> assertEquals(expectedStorageSize, Storage.fruits.size())
        );
    }

    @Test
    void process_emptyList_ok() {
        Storage.fruits.put("apple", 10);
        Storage.fruits.put("banana", 50);
        List<FruitTransaction> transactions = new ArrayList<>();
        int expectedAppleQuantity = 10;
        int expectedBananaQuantity = 50;
        int expectedStorageSize = 2;
        shopService.process(transactions);
        assertAll(
                () -> assertEquals(expectedAppleQuantity, Storage.fruits.get("apple")),
                () -> assertEquals(expectedBananaQuantity, Storage.fruits.get("banana")),
                () -> assertEquals(expectedStorageSize, Storage.fruits.size())
        );
    }

    @Test
    void process_nullList_notOk() {
        assertThrows(NullPointerException.class,
                () -> shopService.process(null));
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }
}
