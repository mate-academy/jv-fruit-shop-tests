package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecordDto;
import core.basesyntax.service.FruitOperation;
import core.basesyntax.service.operations.OperationType;
import core.basesyntax.validate.RemoveOperationValidator;
import core.basesyntax.validate.impl.RemoveOperationValidatorImpl;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class RemoveOperationTest {
    private static FruitOperation operation;
    private static FruitRecordDto fruitRecordDtoTest;
    private static RemoveOperationValidator removeOperationValidator;

    @BeforeClass
    public static void beforeClass() throws Exception {
        operation = new RemoveOperation();
        fruitRecordDtoTest = new FruitRecordDto(OperationType.b,"banana",20);
        removeOperationValidator = new RemoveOperationValidatorImpl();
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }

    @Test
    public void removeAmountTest_Ok() {
        Storage.fruits.put(new Fruit("banana"),30);
        assertEquals(10,operation.apply(fruitRecordDtoTest));
    }

    @Test (expected = RuntimeException.class)
    public void removeAmountWithNotEnoughValue_NotOk() {
        Storage.fruits.put(new Fruit("banana"),1);
        operation.apply(fruitRecordDtoTest);
    }

    @Test
    public void removeValidationTest_Ok() {
        Integer firstTest = 20;
        Integer secondTest = 15;
        assertTrue(removeOperationValidator.removeOperationValidate(firstTest,secondTest));
    }

    @Test (expected = RuntimeException.class)
    public void removeValidationWithIncorrectParamsTest_NotOk() {
        removeOperationValidator.removeOperationValidate(5,10);
    }
}
