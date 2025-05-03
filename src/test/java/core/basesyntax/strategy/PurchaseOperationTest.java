package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.FruitDao;
import core.basesyntax.db.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {

    @Test
    void purchase_Ok() {
        FruitDao fruitDao = new FruitDaoImpl();
        FruitTransaction transaction = new FruitTransaction();
        fruitDao.updateFruitQuantity("apple", 5);
        transaction.setFruit("apple");
        transaction.setQuantity(5);

        PurchaseOperation handler = new PurchaseOperation();
        handler.executeOperation(fruitDao, transaction);

        assertEquals(0, fruitDao.getAllFruits().get("apple"));
    }
}
