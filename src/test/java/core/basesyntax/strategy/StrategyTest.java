package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StrategyTest {

    @BeforeAll
    static void beforeAll() {
        Storage.storage.clear();
    }

    @BeforeEach
    void beforeEach() {
        Storage.storage.put("banana", 200);
    }

    @Test
    void handler_BalanceCorrectWork_Ok() {
        BalanceHandler balanceHandler = new BalanceHandler();
        balanceHandler.execute("banana", 100);
        assertEquals(Storage.storage.get("banana"), 100);
    }

    @Test
    void handler_PurchaseCorrectWork_Ok() {
        PurchaseHandler purchaseHandler = new PurchaseHandler();
        purchaseHandler.execute("banana", 50);
        assertEquals(Storage.storage.get("banana"), 150);
    }

    @Test
    void handler_SupplyCorrectWork_Ok() {
        SupplyHandler supplyHandler = new SupplyHandler();
        supplyHandler.execute("banana", 50);
        assertEquals(Storage.storage.get("banana"), 250);
    }

    @Test
    void handler_ReturnCorrectWork_Ok() {
        ReturnHandler returnHandler = new ReturnHandler();
        returnHandler.execute("banana", 50);
        assertEquals(Storage.storage.get("banana"), 250);
    }

    @AfterEach
    void afterEach() {
        Storage.storage.clear();
    }
}
