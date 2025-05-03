package core.basesyntax.service.impls;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.DatabaseOperation;
import exceptions.IllegalTransactionException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RemoveFruitOperationTest {
    private Integer excepted;
    private final DatabaseOperation removing = new RemoveFruitOperation();

    @Before
    public void beforeAll() {
        Storage.shopDatabase.put("banana", 10);
    }

    @Test
    public void removing_isOk() {
        excepted = 5;
        removing.apply(new FruitRecordDto("p", "banana", 5));
        assertEquals(excepted,
                Storage.shopDatabase.get("banana"));
    }

    @Test (expected = IllegalTransactionException.class)
    public void removing_negative_throwsException() {
        removing.apply(new FruitRecordDto("p", "banana", -20));
    }

    @Test (expected = IllegalTransactionException.class)
    public void removing_negativeResult_throwsException() {
        removing.apply(new FruitRecordDto("p", "banana", 20));
    }

    @After
    public void tearDown() {
        Storage.shopDatabase.clear();
    }
}
