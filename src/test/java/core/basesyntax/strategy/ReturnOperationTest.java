package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitOperation;
import core.basesyntax.model.Instruction;
import core.basesyntax.strategy.impl.ReturnOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private StorageDao storageDao = new StorageDaoImpl();
    private Operation returnOperation = new ReturnOperation(storageDao);
    private Instruction returnOk = new Instruction(FruitOperation.RETURN,
            "apple", 50);
    private Instruction returnNotExist = new Instruction(FruitOperation.RETURN,
            "banana", 50);
    private Instruction returnNegativeValue = new Instruction(FruitOperation.RETURN,
            "apple", -1);

    @BeforeEach
    public void setUp() {
        Storage.fruits.clear();
        Storage.fruits.put("apple", 100);
    }

    @Test
    void proceed_ReturnOperation_ok() {
        returnOperation.proceed(returnOk);
        int expected = 150;
        int current = Storage.fruits.get(returnOk.getFruitName());
        assertEquals(expected, current,
                "Wrong work of Return operation");
    }

    @Test
    void proceed_ReturnOperationNotExist_noOk() {
        assertThrows(OperationException.class, () -> returnOperation.proceed(returnNotExist),
                "Return operation should work only with existed products");
    }

    @Test
    void proceed_ReturnOperationNegativeQuantity_noOk() {
        assertThrows(OperationException.class, () -> returnOperation.proceed(returnNegativeValue),
                "Return operation shouldn't work with negative value");
    }
}
