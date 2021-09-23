package core.basesyntax.fruitshop.service.operations;

import core.basesyntax.fruitshop.fruitstoragedb.FruitStorage;
import core.basesyntax.fruitshop.model.Fruit;
import core.basesyntax.fruitshop.model.OperationType;
import core.basesyntax.fruitshop.model.RecordDto;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class BalanceOperationHandlerTest {


    @Test(expected = RuntimeException.class)
    public void ContainsBalance_applyOperation_Ok() {
        RecordDto firstBalance = new RecordDto(OperationType.BALANCE, new Fruit("orange"), 40);
        RecordDto secondBalance = new RecordDto(OperationType.BALANCE,  new Fruit("orange"), 10);
        BalanceOperationHandler operationHandler = new BalanceOperationHandler();
        operationHandler.applyOperation(firstBalance);
        operationHandler.applyOperation(secondBalance);
    }
    @After
    public void afterEachTest() throws Exception {
        FruitStorage.getStorage().clear();
    }


 /*   @Test
    public void NotContainsBalance_applyOperation_Ok() {

        RecordDto secondBalance = new RecordDto(OperationType.BALANCE,  new Fruit("orange"), 10);
        BalanceOperationHandler operationHandler = new BalanceOperationHandler();
        assertTrue(FruitStorage.getStorage().isEmpty());
        operationHandler.applyOperation(secondBalance);
        assertFalse(FruitStorage.getStorage().isEmpty());
    }*/
}