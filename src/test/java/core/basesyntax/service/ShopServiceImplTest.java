package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private ShopService service;

    @BeforeEach
    void setUp() {
        Storage.clear();
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());

        OperationStrategy operationStrategy = new OperationStrategyImpl(handlers);
        service = new ShopServiceImpl(operationStrategy);
    }

    @Test
    void process_validTransactions_Ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "apple",
                        100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                        "apple",
                        50),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        "apple",
                        20),
                new FruitTransaction(FruitTransaction.Operation.RETURN,
                        "apple",
                        10)
        );
        service.process(transactions);
        assertEquals(140, Storage.get("apple"));
    }

    @Test
    void process_emptyTransactionList_Ok() {
        List<FruitTransaction> transactions = List.of();
        service.process(transactions);
        assertTrue(Storage.getAll().isEmpty());
    }
}
