package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.FruitTransaction;
import core.basesyntax.db.Database;
import org.junit.jupiter.api.Test;

class BalanceServiceTest {
    private final BalanceService balanceService = new BalanceService();
    private final FruitTransaction fruitTransaction = new FruitTransaction("b","apple",1000);

    @Test
    void correctDataWrittenToDatabase_isOk() {
        balanceService.processTransaction(fruitTransaction);
        int actual = Database.database.get(fruitTransaction.getFruit());
        int expect = fruitTransaction.getQuantity();
        assertEquals(expect, actual);
    }
}
