package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.handler.BalanceOperationHandler;
import core.basesyntax.handler.PurchaseOperationHandler;
import core.basesyntax.handler.ReturnOperationHandler;
import core.basesyntax.handler.SupplyOperationHandler;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {
    private final OperationStrategy operationStrategy = new OperationStrategyImpl(Map.of(
            FruitTransaction.Operation.RETURN, new ReturnOperationHandler(),
            FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(),
            FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(),
            FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler()
    ));
    private final ShopService shopService = new ShopServiceImpl(operationStrategy);

    @AfterEach
    void clearStorage() {
        Storage.getStorage().clear();
    }

    @Test
    void process_validTransactions_ok() {
        List<FruitTransaction> transactionList = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 3)
        );

        shopService.process(transactionList);

        assertEquals(28, Storage.getStorage().get("banana"));
    }
}
