package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.impl.FruitTransactionDaoIml;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionServiceImplTest {
    private FruitTransactionDao fruitTransactionDao;
    private FruitTransactionService fruitTransactionService;
    private FruitTransaction fruitTransaction;
    private String nameFruit;
    private int quantity;

    @BeforeEach
    void setUp() {
        fruitTransactionDao = new FruitTransactionDaoIml();
        fruitTransactionService = new FruitTransactionServiceImpl(fruitTransactionDao);
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit(nameFruit);
        fruitTransaction.setQuantity(quantity);
        nameFruit = "Apple";
        quantity = 10;
    }

    @Test
    void createNewFruitTransaction_ok() {
        fruitTransactionService.createNewFruitTransaction(nameFruit, quantity);
        assertTrue(fruitTransactionDao.getAllListDb().contains(fruitTransaction));
        assertEquals(nameFruit, fruitTransactionDao.get(nameFruit).getFruit());
        assertEquals(quantity, fruitTransactionDao.get(nameFruit).getQuantity());
    }
}
