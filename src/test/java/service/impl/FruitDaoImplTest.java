package service.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.FruitDao;

class FruitDaoImplTest {
    private static FruitDao fruitDao;

    @BeforeAll
    static void setUp() {
        fruitDao = new FruitDaoImpl();
    }

    @Test
    void getFromCsvData_NullLine_notOk() {
        assertThrows(RuntimeException.class, () -> fruitDao.getFromCsvData(null));
    }

    @Test
    void validCsvLine_parsesCorrectly_Ok() {
        String line = "b,banana,42";
        FruitTransaction fruitTransaction = fruitDao.getFromCsvData(line);

        assertEquals(FruitTransaction.Operation.BALANCE, fruitTransaction.getOperation());
        assertEquals("banana", fruitTransaction.getFruit());
        assertEquals(42, fruitTransaction.getQuantity());
    }

    @Test
    void invalidCsvLine_wrongFieldCount_notOk() {
        String line = "b,banana";
        assertThrows(RuntimeException.class, () -> fruitDao.getFromCsvData(line));
    }

    @Test
    void invalidQuantity_nonNumeric_notOk() {
        String line = "b,banana,abc";
        assertThrows(RuntimeException.class, () -> fruitDao.getFromCsvData(line));
    }

    @Test
    void invalidQuantity_negativeNumber_notOk() {
        String line = "b,banana,-25";
        assertThrows(RuntimeException.class, () -> fruitDao.getFromCsvData(line));
    }
}
