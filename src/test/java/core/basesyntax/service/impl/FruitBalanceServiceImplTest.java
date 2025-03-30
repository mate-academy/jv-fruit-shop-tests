package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import core.basesyntax.dao.FruitBalanceDao;
import core.basesyntax.model.FruitBalance;
import core.basesyntax.service.FruitBalanceService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FruitBalanceServiceImplTest {
    @Mock
    private FruitBalanceDao fruitBalanceDao;
    private FruitBalanceService fruitBalanceService;

    @BeforeEach
    void setUp() {
        fruitBalanceService = new FruitBalanceServiceImpl(fruitBalanceDao);
    }

    @Test
    void updateFruitBalance_fruitExistsInDataBase_ok() {
        String fruit = "banana";
        int newBalance = 100;
        FruitBalance existingFruit = new FruitBalance("banana", 50);
        when(fruitBalanceDao.get()).thenReturn(List.of(existingFruit));
        fruitBalanceService.updateFruitBalance(fruit, newBalance);
        verify(fruitBalanceDao, never()).add(any());
        assertEquals(newBalance, existingFruit.getBalance());
    }

    @Test
    void updateFruitBalance_fruitNotExists_ok() {
        String fruit = "banana";
        int newBalance = 100;
        when(fruitBalanceDao.get()).thenReturn(List.of());
        fruitBalanceService.updateFruitBalance(fruit, newBalance);
        ArgumentCaptor<FruitBalance> captor = ArgumentCaptor.forClass(FruitBalance.class);
        verify(fruitBalanceDao).add(captor.capture());
        FruitBalance capturedFruitBalance = captor.getValue();
        assertEquals(fruit, capturedFruitBalance.getFruit());
        assertEquals(newBalance, capturedFruitBalance.getBalance());
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
}
