package core.basesyntax.service.operations;

import static core.basesyntax.service.operations.TetsObjects.INVALID_BALANCE_DTO;
import static core.basesyntax.service.operations.TetsObjects.VALID_BALANCE_DTO;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exeptions.UnsupportedOperationExeption;
import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationHandlerTest {
    private static final int EXPECTED_QUANTITY = 10;
    private BalanceOperationHandler balanceOperationHandler;

    @BeforeEach
    public void setUp() {
        balanceOperationHandler = new BalanceOperationHandler();
        Storage.fruits.clear();
    }

    @Test
    public void apply_ValidTransactionDto_Ok() {
        balanceOperationHandler.apply(VALID_BALANCE_DTO);
        assertEquals(EXPECTED_QUANTITY, Storage.fruits.get(new Fruit("apple")));
    }

    @Test
    public void apply_InvalidTransactionDto_NotOk() {
        assertThrows(UnsupportedOperationExeption.class, ()
                -> balanceOperationHandler.apply(INVALID_BALANCE_DTO));
    }

    @Test
    public void isApplicable_ValidBalanceOperation_Ok() {
        assertTrue(balanceOperationHandler.isApplicable(VALID_BALANCE_DTO));
    }

    @Test
    public void isApplicable_NonBalanceOperation_NotOk() {
        assertFalse(balanceOperationHandler.isApplicable(INVALID_BALANCE_DTO));
    }

    @Test
    public void apply_NullTransactionDto_NotOk() {
        assertThrows(UnsupportedOperationExeption.class, () -> balanceOperationHandler.apply(null));
    }
}
