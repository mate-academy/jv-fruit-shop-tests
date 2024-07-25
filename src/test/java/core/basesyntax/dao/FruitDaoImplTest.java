package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.domain.FruitTransaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FruitDaoImplTest {

    @Test
    @DisplayName("FruitDao add fruitTransaction test")
    void addFruitTransaction_Ok() {
        FruitDao fruitDao = new FruitDaoImpl();
        fruitDao.add(new FruitTransaction(FruitTransaction.FruitName.APPLE, 10));
        int expectedSizeAfterInsert = 1;
        int actualSizeAfterInsert = Storage.getFruits().size();
        assertEquals(expectedSizeAfterInsert, actualSizeAfterInsert);
        Storage.getFruits().clear();
    }
}
