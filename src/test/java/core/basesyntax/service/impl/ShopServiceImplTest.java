package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.handler.OperationHandler;
import core.basesyntax.handler.impl.BalanceOperation;
import core.basesyntax.handler.impl.PurchaseOperation;
import core.basesyntax.handler.impl.ReturnOperation;
import core.basesyntax.handler.impl.SupplyOperation;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private ShopService shopService;
    private Map<String, Integer> fruitStorage;

    @BeforeEach
    void setUp() {
        fruitStorage = new HashMap<>();
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(fruitStorage),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(fruitStorage),
                FruitTransaction.Operation.RETURN, new ReturnOperation(fruitStorage),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation(fruitStorage)
        );
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @Test
    void process_validTransactions_ok() {
        FruitTransaction transaction1 = new FruitTransaction();
        transaction1.setOperation(FruitTransaction.Operation.BALANCE);
        transaction1.setFruit("banana");
        transaction1.setQuantity(100);

        FruitTransaction transaction2 = new FruitTransaction();
        transaction2.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction2.setFruit("banana");
        transaction2.setQuantity(50);

        FruitTransaction transaction3 = new FruitTransaction();
        transaction3.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction3.setFruit("banana");
        transaction3.setQuantity(30);

        List<FruitTransaction> transactions = List.of(transaction1, transaction2, transaction3);
        shopService.process(transactions);
        assertEquals(120, fruitStorage.get("banana"));
    }
}
