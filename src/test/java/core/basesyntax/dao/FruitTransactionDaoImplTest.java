package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.impl.FruitTransactionDaoImpl;
import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class FruitTransactionDaoImplTest {
    private final FruitTransactionDao dao = new FruitTransactionDaoImpl();

    @AfterEach
    void tearDown() {
        Storage.listFruits.clear();
    }

    @Test
    public void getAllDataTest_valid_Ok() {
        Storage.listFruits.put("banan", 8);
        Storage.listFruits.put("apple", 24);

        String result = "apple,24" + System.lineSeparator()
                + "banan,8" + System.lineSeparator();
        String getAllDataString = dao.getAllData();
        assertEquals(result, getAllDataString);
    }
}
