package strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import db.Storage;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {

    private BalanceOperation balanceOperation;

    @BeforeEach
    void setUp() {
        balanceOperation = new BalanceOperation();
        Storage.clear();
    }

    @Test
    void apply_setsQuantityCorrectly() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                "apple",
                10
        );

        balanceOperation.apply(transaction);

        assertEquals(10, Storage.getQuantity("apple"));
    }

    @Test
    void apply_overwritesPreviousQuantity() {
        Storage.setQuantity("apple", 5);

        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                "apple",
                12
        );

        balanceOperation.apply(transaction);

        assertEquals(12, Storage.getQuantity("apple"));
    }
}
