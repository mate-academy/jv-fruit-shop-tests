package core.basesyntax.service.impls;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.DatabaseOperation;
import exceptions.IllegalTransactionException;
import org.junit.After;
import org.junit.Test;

public class AddFruitOperationTest {
    private Integer excepted;
    private final DatabaseOperation addition = new AddFruitOperation();

    @Test
    public void addition_toEmptyDb_isOk() {
        excepted = 20;
        addition.apply(new FruitRecordDto("s", "banana", 20));
        assertEquals(excepted, Storage.shopDatabase.get("banana"));
    }

    @Test
    public void addition_isOk() {
        Storage.shopDatabase.put("banana", 10);
        excepted = 30;
        addition.apply(new FruitRecordDto("s", "banana", 20));
        assertEquals(excepted,
                Storage.shopDatabase.get("banana"));
    }

    @Test (expected = IllegalTransactionException.class)
    public void addition_negative_throwsException() {
        addition.apply(new FruitRecordDto("s", "banana", -20));
    }

    @After
    public void tearDown() {
        Storage.shopDatabase.clear();
    }
}
