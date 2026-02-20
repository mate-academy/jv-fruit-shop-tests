package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.OperationBalanceStrategyImpl;
import core.basesyntax.strategy.impl.OperationPurchareStrategyImpl;
import core.basesyntax.strategy.impl.OperationReturnStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static FruitTransaction fruitTransaction;
    private static FruitTransaction fruitTransaction2;
    private static FruitTransaction fruitTransaction3;
    private static List<FruitTransaction> fruitTransactionList;
    private Map<FruitTransaction.Operation, OperationStrategy> operationHandlers;
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        Storage.getStorage().clear();
        operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE,
                        new OperationBalanceStrategyImpl());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE,
                new OperationPurchareStrategyImpl());
        operationHandlers.put(FruitTransaction.Operation.RETURN,
                new OperationReturnStrategyImpl());
        shopService = new ShopServiceImpl(operationHandlers);

        fruitTransaction = new FruitTransaction();
        fruitTransaction2 = new FruitTransaction();
        fruitTransaction3 = new FruitTransaction();

        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setQuantity(20);
        fruitTransaction.setFruit("banana");

        fruitTransaction2.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction2.setQuantity(5);
        fruitTransaction2.setFruit("apple");

        fruitTransaction3.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction3.setQuantity(3);
        fruitTransaction3.setFruit("banana");
        fruitTransactionList = List.of(fruitTransaction, fruitTransaction2, fruitTransaction3);
    }

    @Test
    void notValidSupplyOperation() {
        fruitTransaction3.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransactionList = List.of(fruitTransaction, fruitTransaction2, fruitTransaction3);
        assertThrows(RuntimeException.class, () -> shopService.process(fruitTransactionList));
    }

    @Test
    void validFruitTransactions() {
        Integer expectedQuantityBanana = fruitTransaction.getQuantity()
                + fruitTransaction3.getQuantity();
        Integer expectedQuantityApple = fruitTransaction2.getQuantity();

        assertDoesNotThrow(() -> shopService.process(fruitTransactionList));
        assertEquals(expectedQuantityBanana, Storage.getStorage().get("banana"));
        assertEquals(expectedQuantityApple, Storage.getStorage().get("apple"));
    }
}
