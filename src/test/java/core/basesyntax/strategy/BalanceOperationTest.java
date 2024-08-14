package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitOperation;
import core.basesyntax.model.Instruction;
import core.basesyntax.strategy.impl.BalanceOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private StorageDao storageDao = new StorageDaoImpl();
    private Operation balance = new BalanceOperation(storageDao);
    private Instruction balanceOk = new Instruction(FruitOperation.BALANCE,
            "banana", 50);
    private Instruction balanceAlreadyExist = new Instruction(FruitOperation.BALANCE,
            "apple", 50);
    private Instruction balanceNegativeQuantity = new Instruction(FruitOperation.BALANCE,
            "banana", -1);

    @BeforeEach
    public void setUp() {
        Storage.fruits.clear();
        Storage.fruits.put("apple", 100);
    }

    @Test
    void proceed_BalanceOperation_ok() {
        balance.proceed(balanceOk);
        boolean expected = true;
        assertEquals(expected,
                Storage.fruits.containsKey(balanceOk.getFruitName())
                        && Storage.fruits.containsValue(balanceOk.getQuantity()),
                "Balance operation don't add the fruit");
    }

    @Test
    void proceed_BalanceOperationAlreadyExist_notOk() {
        assertThrows(OperationException.class, () ->
                balance.proceed(balanceAlreadyExist), "Balance operation add existed fruit");
    }

    @Test
    void proceed_BalanceOperationNegativeQuantity_notOk() {
        assertThrows(OperationException.class, () ->
                balance.proceed(balanceNegativeQuantity),
                "Balance operation add fruit with negative quantity");
    }
}
