package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {
    private static ShopService shopService;

    @BeforeAll
    public static void initialize() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @AfterEach
    public void clear() {
        Storage.fruitStorage.clear();
    }

    @Test
    public void process_balanceOperation_Ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                "banana",
                20
        );
        shopService.process(List.of(transaction));
        assertEquals(Integer.valueOf(20), Storage.fruitStorage.get("banana"));
    }

    @Test
    public void process_supplyOperation_Ok() {
        Storage.fruitStorage.put("banana", 10);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                "banana",
                20
        );
        shopService.process(List.of(transaction));
        assertEquals(Integer.valueOf(30), Storage.fruitStorage.get("banana"));
    }

    @Test
    public void process_purchaseOperation_Ok() {
        Storage.fruitStorage.put("banana", 20);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                "banana",
                10
        );
        shopService.process(List.of(transaction));
        assertEquals(Integer.valueOf(10), Storage.fruitStorage.get("banana"));
    }

    @Test
    public void process_purchaseMoreThanAvailable_notOk() {
        Storage.fruitStorage.put("banana", 20);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                "banana",
                30
        );
        assertThrows(RuntimeException.class, () -> shopService.process(List.of(transaction)));
    }

    @Test
    public void process_returnOperation_Ok() {
        Storage.fruitStorage.put("banana", 20);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN,
                "banana",
                30
        );
        shopService.process(List.of(transaction));
        assertEquals(Integer.valueOf(50), Storage.fruitStorage.get("banana"));
    }

    @Test
    public void process_multipleOperations_Ok() {
        Storage.fruitStorage.put("banana", 20);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN,
                "banana",
                30
        );
        shopService.process(List.of(transaction));
        FruitTransaction transaction2 = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                "banana",
                10
        );
        shopService.process(List.of(transaction2));
        assertEquals(Integer.valueOf(40), Storage.fruitStorage.get("banana"));
    }
}
