package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecordDto;
import core.basesyntax.service.FruitOperation;
import core.basesyntax.service.operations.OperationType;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddOperationTest {
    private static FruitOperation operation;
    private static FruitRecordDto fruitRecordDtoTest;

    @BeforeClass
    public static void beforeClass() throws Exception {
        operation = new AddOperation();
        fruitRecordDtoTest = new FruitRecordDto(OperationType.b,"banana",10);
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }

    @Test
    public void addAmountTest_Ok() {
        Storage.fruits.put(new Fruit("banana"),5);
        assertEquals(15,operation.apply(fruitRecordDtoTest));
    }

    @Test
    public void addFruitWithNullValue_NotOk() {
        Fruit fruit = new Fruit(null);
        Storage.fruits.put(fruit,null);
        assertNull(Storage.fruits.get(fruit));
    }
}
