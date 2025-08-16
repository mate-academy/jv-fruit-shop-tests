package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperation;
import core.basesyntax.strategy.impl.ReturnOperation;
import core.basesyntax.strategy.impl.SupplyOperation;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import core.basesyntax.db.Storage;

public class ShopServiceImplTest {
    private ShopServiceImpl shopService;
    private OperationStrategy operationStrategy;

    @BeforeEach
    public void setUp() {
        Map<FruitTransaction.Operation, core.basesyntax.strategy.OperationHandler> handlerMap = new java.util.HashMap<>();
        handlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        handlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlerMap.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationStrategy = new OperationStrategyImpl(handlerMap);
        shopService = new ShopServiceImpl(operationStrategy);
        Storage.getFruitStorage().clear();
    }

    @Test
    public void process_correctData_ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 50),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 10)
        );
        shopService.process(transactions);
        Assertions.assertEquals(90, Storage.getFruitStorage().get("banana"));
        Assertions.assertEquals(70, Storage.getFruitStorage().get("apple"));
    }

    @Test
    public void process_nullTransactionsList_throwsException() {
        assertThrows(RuntimeException.class, () -> shopService.process(null));
    }

    @Test
    public void process_emptyTransactionsList_doesNothing() {
        shopService.process(Collections.emptyList());
        Assertions.assertTrue(Storage.getFruitStorage().isEmpty());
    }
}
