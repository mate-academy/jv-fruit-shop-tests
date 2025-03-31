package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.FruitBalanceDao;
import core.basesyntax.dao.FruitBalanceDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitBalance;
import core.basesyntax.service.FruitBalanceService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitBalanceServiceImplTest {
    private FruitBalanceDao fruitBalanceDao;
    private FruitBalanceService fruitBalanceService;

    @BeforeEach
    void setUp() {
        fruitBalanceDao = new FruitBalanceDaoImpl();
        fruitBalanceService = new FruitBalanceServiceImpl(fruitBalanceDao);
    }

    @Test
    void updateFruitBalance_fruitExistsInDataBase_ok() {
        Storage.fruitBalances.add(new FruitBalance("banana", 50));
        String fruit = "banana";
        int newBalance = 100;
        fruitBalanceService.updateFruitBalance(fruit, newBalance);
        int actualBalance = fruitBalanceDao.get(fruit).getBalance();
        assertEquals(newBalance, actualBalance);
        Storage.fruitBalances.clear();
    }

    @Test
    void updateFruitBalance_fruitNotExists_ok() {
        assertTrue(Storage.fruitBalances.isEmpty());
        String fruit = "banana";
        int newBalance = 100;
        fruitBalanceService.updateFruitBalance(fruit, newBalance);
        assertEquals(1, Storage.fruitBalances.size());
        assertTrue(Storage.fruitBalances.stream()
                .anyMatch(fb -> fb.getFruit().equals(fruit) && fb.getBalance() == newBalance));
    }

    @Test
    void updateFruitBalance_negativeBalanceValue_notOk() {
        String fruit = "banana";
        int newBalance = -100;
        Exception exception = assertThrows(RuntimeException.class,
                () -> fruitBalanceService.updateFruitBalance(fruit, newBalance));
        assertEquals("Balance value can't be negative: " + newBalance,
                exception.getMessage());
    }

    @AfterEach
    public void afterEachTest() {
        Storage.fruitBalances.clear();
    }
}
