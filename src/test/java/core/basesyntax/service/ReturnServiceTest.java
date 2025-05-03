package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.FruitTransaction;
import core.basesyntax.db.Database;
import org.junit.jupiter.api.Test;

class ReturnServiceTest {
    private final ReturnService service = new ReturnService();
    private final FruitTransaction fruitTransaction = new FruitTransaction("r","banana",55);

    @Test
    void correctDataWrittenToDatabase_isOk() {
        service.processTransaction(fruitTransaction);
        int actual = Database.database.get(fruitTransaction.getFruit());
        int expect = fruitTransaction.getQuantity();
        assertTrue(expect <= actual);
    }
}
