package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {

    private OperationStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new OperationStrategy();
        Storage.DATA.clear();
    }

    @Test
    void processData_balanceApple_ok() {
        FruitTransaction transaction = new FruitTransaction(Operations.BALANCE, "apple", 100);
        strategy.processData(transaction);
        assertEquals(100, Storage.DATA.get("apple"));
    }

    @Test
    void processData_purchaseBanana_ok() {
        Storage.DATA.put("banana", 150);
        strategy.processData(new FruitTransaction(Operations.PURCHASE, "banana", 50));
        assertEquals(100, Storage.DATA.get("banana"));
    }

    @Test
    void processData_returnAppleWithoutInitialBalance_ok() {
        FruitTransaction returnTransaction = new FruitTransaction(Operations.RETURN, "apple", 30);
        strategy.processData(returnTransaction);
        assertEquals(30, Storage.DATA.get("apple"));
    }

    @Test
    void processData_supplyBananaWithoutInitialBalance_ok() {
        FruitTransaction supplyTransaction = new FruitTransaction(Operations.SUPPLY, "banana", 50);
        strategy.processData(supplyTransaction);
        assertEquals(50, Storage.DATA.get("banana"));
    }

    @Test
    void processData_nullTransaction_notOk() {
        assertThrows(RuntimeException.class, () -> strategy.processData(null));
    }
}

