package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.FruitDao;
import core.basesyntax.db.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {

    @Test
    void supply_Ok() {
        FruitDao fruitDao = new FruitDaoImpl();
        FruitTransaction transaction = new FruitTransaction();
        SupplyOperation handler = new SupplyOperation();

        transaction.setFruit("apple");
        transaction.setQuantity(5);
        handler.executeOperation(fruitDao, transaction);

        assertEquals(5, fruitDao.getAllFruits().get("apple"));
    }
}
