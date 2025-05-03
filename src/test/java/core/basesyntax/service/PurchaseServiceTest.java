package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.FruitTransaction;
import core.basesyntax.db.Database;
import org.junit.jupiter.api.Test;

class PurchaseServiceTest {
    private final PurchaseService purchaseService = new PurchaseService();
    private final BalanceService balanceService = new BalanceService();
    private final FruitTransaction fruitTransaction = new FruitTransaction("p","orange",1);

    @Test
    void correctDataWrittenToDatabase_isOk() {
        balanceService.processTransaction(fruitTransaction);
        int expect = fruitTransaction.getQuantity();
        purchaseService.processTransaction(fruitTransaction);
        int actual = Database.database.get(fruitTransaction.getFruit());
        assertTrue(expect >= actual);
    }

    @Test
    void fruitsInDatabaseLessThanOperation_notOk() {
        assertThrows(RuntimeException.class,
                () -> purchaseService.processTransaction(fruitTransaction));
    }
}
