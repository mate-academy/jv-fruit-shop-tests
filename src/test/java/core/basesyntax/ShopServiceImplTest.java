package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.ShopService;
import core.basesyntax.strategy.ShopServiceImpl;
import core.basesyntax.strategy.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        shopService = new ShopServiceImpl(new OperationStrategyImpl(operationHandlers));
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }

    @Test
    void process_validTransactions_ok() {
        FruitTransaction balanceTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 100);
        FruitTransaction supplyTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 50);

        List<FruitTransaction> transactions = List.of(balanceTransaction, supplyTransaction);

        shopService.process(transactions);
        assertEquals(150, Storage.getAll().get("apple"));
        assertNull(Storage.getAll().get("banana"));
    }

    @Test
    void process_emptyTransactionList_ok() {
        shopService.process(List.of());
        assertEquals(0, Storage.getAll().size());
    }
}
