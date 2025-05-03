package core.basesyntax.services.imps;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseHandler;
import core.basesyntax.strategy.ReturnHandler;
import core.basesyntax.strategy.SupplyHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitServiceImpTest {
    private static FruitServiceImp fruitService;

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.Operation, OperationHandler> operationStrategyMap = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceHandler(),
                FruitTransaction.Operation.PURCHASE, new PurchaseHandler(),
                FruitTransaction.Operation.SUPPLY, new SupplyHandler(),
                FruitTransaction.Operation.RETURN, new ReturnHandler());
        fruitService = new FruitServiceImp(operationStrategyMap);
    }

    @Test
    void store_emptyTransactions_ok() {
        List<FruitTransaction> transactions = new ArrayList<>();
        fruitService.store(transactions);
        assertTrue(Storage.fruitInventory.isEmpty());
    }

    @Test
    void store_validInput_ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 14),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 88));
        fruitService.store(transactions);
        assertTrue(Storage.fruitInventory.get("apple") == 14
                && Storage.fruitInventory.get("banana") == 88);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitInventory.clear();
    }
}
