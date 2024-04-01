package core.basesyntax.service.operations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dto.FruitTransactionDto;
import core.basesyntax.exeptions.UnsupportedOperationExeption;
import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationHandlerTest {
    private BalanceOperationHandler balanceOperationHandler;

    @BeforeEach
    public void setUp() {
        balanceOperationHandler = new BalanceOperationHandler();
        Storage.fruits.clear();
    }

    @Test
    public void apply_ValidTransactionDto_Ok() {
        FruitTransactionDto dto = new FruitTransactionDto("b", "apple", 10);
        balanceOperationHandler.apply(dto);
        assertEquals(10, Storage.fruits.get(new Fruit("apple")));
    }

    @Test
    public void apply_InvalidTransactionDto_NotOk() {
        FruitTransactionDto dto = new FruitTransactionDto("a", "apple", 10);
        assertThrows(UnsupportedOperationExeption.class, () -> balanceOperationHandler.apply(dto));
    }

    @Test
    public void isApplicable_ValidBalanceOperation_Ok() {
        FruitTransactionDto dto = new FruitTransactionDto("b", "apple", 10);
        assertTrue(balanceOperationHandler.isApplicable(dto));
    }

    @Test
    public void isApplicable_NonBalanceOperation_NotOk() {
        FruitTransactionDto dto = new FruitTransactionDto("s", "apple", 10);
        assertFalse(balanceOperationHandler.isApplicable(dto));
    }

    @Test
    public void apply_NullTransactionDto_NotOk() {
        assertThrows(UnsupportedOperationExeption.class, () -> balanceOperationHandler.apply(null));
    }
}
