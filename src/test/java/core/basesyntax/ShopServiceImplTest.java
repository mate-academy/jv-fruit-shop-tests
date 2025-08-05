package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.OperationStrategyImpl;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.ShopServiceImpl;
import core.basesyntax.service.operations.BalanceOperation;
import core.basesyntax.service.operations.OperationHandler;
import core.basesyntax.service.operations.PurchaseOperation;
import core.basesyntax.service.operations.ReturnOperation;
import core.basesyntax.service.operations.SupplyOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {
    private static ShopService shopService;

    @BeforeAll
    static void setUp() {
        Map<Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(Operation.SUPPLY, new SupplyOperation());
        operationHandlers.put(Operation.RETURN, new ReturnOperation());

        OperationStrategy strategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(strategy);
    }

    @AfterEach
    void clearStorage() {
        FruitStorage.storage.clear();
    }

    @Test
    public void transfer_balanceOperation_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, "banana", 100);
        shopService.transfer(transaction);
        assertEquals(Integer.valueOf(100), FruitStorage.storage.get("banana"));
    }

    @Test
    public void transfer_supplyOperation_Ok() {
        FruitStorage.storage.put("apple", 30);
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "apple", 20);
        shopService.transfer(transaction);
        assertEquals(Integer.valueOf(50), FruitStorage.storage.get("apple"));
    }

    @Test
    public void transfer_purchaseOperation_Ok() {
        FruitStorage.storage.put("apple", 40);
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, "apple", 15);
        shopService.transfer(transaction);
        assertEquals(Integer.valueOf(25), FruitStorage.storage.get("apple"));
    }

    @Test
    public void transfer_returnOperation_Ok() {
        FruitStorage.storage.put("apple", 10);
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, "apple", 5);
        shopService.transfer(transaction);
        assertEquals(Integer.valueOf(15), FruitStorage.storage.get("apple"));
    }

    @Test
    public void transfer_NullOperation_NotOk() {
        assertThrows(IllegalArgumentException.class, () ->
                shopService.transfer(new FruitTransaction(null, "banana", 50))
        );
    }

    @Test
    public void transfer_NullQuantity_NotOk() {
        assertThrows(IllegalArgumentException.class, () ->
                shopService.transfer(new FruitTransaction(Operation.BALANCE, "banana", null))
        );
    }

    @Test
    public void transfer_NullFruit_NotOk() {
        assertThrows(IllegalArgumentException.class, () ->
                shopService.transfer(new FruitTransaction(Operation.BALANCE, null, 50))
        );
    }
}
