package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.FruitTransaction;
import core.basesyntax.db.Database;
import org.junit.jupiter.api.Test;

class SupplyServiceTest {
    private final SupplyService service = new SupplyService();
    private final FruitTransaction fruitTransaction = new FruitTransaction("s","orange",12345);

    @Test
    void correctDataWrittenToDatabase_isOk() {
        service.processTransaction(fruitTransaction);
        int actual = Database.database.get(fruitTransaction.getFruit());
        int expect = fruitTransaction.getQuantity();
        assertTrue(expect <= actual);
    }
}
