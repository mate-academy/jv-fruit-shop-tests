package core.basesyntax.services.imps;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.services.FruitService;
import core.basesyntax.strategy.BalanceHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseHandler;
import core.basesyntax.strategy.ReturnHandler;
import core.basesyntax.strategy.SupplyHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitServiceImpTest {
    private FruitService fruitService;
    private List<FruitTransaction> transactions;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationStrategyMap = new HashMap<>();
        operationStrategyMap.put(
                FruitTransaction.Operation.BALANCE, new BalanceHandler());
        operationStrategyMap.put(
                FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        operationStrategyMap.put(
                FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        operationStrategyMap.put(
                FruitTransaction.Operation.RETURN, new ReturnHandler());
        fruitService = new FruitServiceImp(operationStrategyMap);
    }

    @Test
    void store_emptyTransactions_ok() {
        transactions = new ArrayList<>();
        fruitService.store(transactions);
        assertTrue(Storage.fruitInventory.isEmpty());
    }

    @Test
    void store_validInput_ok() {
        transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 14));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 88));
        fruitService.store(transactions);
        assertTrue(Storage.fruitInventory.get("apple") == 14
                && Storage.fruitInventory.get("banana") == 88);
    }

    @AfterEach
    void tearDown() {
        transactions.clear();
        Storage.fruitInventory.clear();
    }
}
