package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.handler.OperationStrategy;
import core.basesyntax.handler.OperationStrategyImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operations.BalanceOperation;
import core.basesyntax.operations.PurchaseOperation;
import core.basesyntax.operations.ReturnOperation;
import core.basesyntax.operations.SupplyOperation;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.ShopServiceImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceTest {
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();

        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(handlers);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @Test
    void shopServiceTest_validTransaction_ok() {
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 0));
        transactions.add(new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 10));
        transactions.add(new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 5));
        transactions.add(new FruitTransaction(
                FruitTransaction.Operation.RETURN, "banana", 5));
        shopService.process(transactions);
        Integer banana = Storage.fruits.getOrDefault("banana", 0);
        Assertions.assertEquals(10, banana);
    }

    @Test
    void shopServiceTest_invalidTransaction_notOk() {
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", -50));
        transactions.add(new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 20));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> shopService.process(transactions));
    }
}
