package core.basesyntax;

import static org.junit.Assert.assertEquals;

import db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import strategy.BalanceOperation;

public class BalanceOperationTest {
    private final BalanceOperation handling = new BalanceOperation();

    @BeforeEach
    void setUp() {
        Storage.fruitStorage.clear();
    }

    @Test
    public void handling_Ok() {
        FruitTransaction fruit = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 50);
        handling.handle(fruit);
        int testing = Storage.fruitStorage.get("banana");
        assertEquals(50, testing);
    }

    @Test
    public void settingNewBalance_Ok() {
        FruitTransaction fruit = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 50);
        handling.handle(fruit);

        FruitTransaction fruitWithSameName =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana",
                100);
        handling.handle(fruitWithSameName);

        int testing = Storage.fruitStorage.get("banana");
        assertEquals(150,testing);
    }
}
