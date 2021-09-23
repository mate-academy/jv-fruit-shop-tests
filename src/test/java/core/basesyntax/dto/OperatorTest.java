package core.basesyntax.dto;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDaoService;
import core.basesyntax.dao.FruitDaoServiceImp;
import core.basesyntax.dto.handlers.BalanceOperationHandler;
import core.basesyntax.dto.handlers.OperationsHandler;
import core.basesyntax.dto.handlers.PurchaseOperationHandler;
import core.basesyntax.dto.handlers.ReturnOperationHandler;
import core.basesyntax.dto.handlers.SupplyOperationHandler;
import core.basesyntax.models.Fruit;
import core.basesyntax.models.FruitRecord;
import core.basesyntax.storage.Storage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

public class OperatorTest {
    private static final Storage storage
            = new Storage();
    private static final FruitDaoService daoService
            = new FruitDaoServiceImp(storage);
    private static final FruitRecord bananaRecord
            = new FruitRecord('b', "banana", 20);
    private static final FruitRecord appleRecord
            = new FruitRecord('b', "apple", 100);
    private static final Fruit banana
            = new Fruit("banana", 20);
    private static final Fruit apple
            = new Fruit("apple", 100);
    private static final Operator operator = new Operator();

    @Before
    public void setUp() {
        Map<Character, OperationsHandler> typesOfOperations
                = operator.getTypesOfOperations();
        typesOfOperations.put('b', new BalanceOperationHandler());
        typesOfOperations.put('p', new PurchaseOperationHandler());
        typesOfOperations.put('r', new ReturnOperationHandler());
        typesOfOperations.put('s', new SupplyOperationHandler());
    }

    @Test
    public void doAllOperation_Ok() {
        List<FruitRecord> recordList = new ArrayList<>();
        recordList.add(bananaRecord);
        recordList.add(appleRecord);
        Set<Fruit> fruitSet = operator.doAllOperation(recordList, daoService);
        boolean expected = true;
        boolean actual = fruitSet.contains(apple) && fruitSet.contains(banana);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void doAllOperation_nullInList_Ok() {
        operator.doAllOperation(null, daoService);
    }

    @Test(expected = RuntimeException.class)
    public void doAllOperation_nullList_Ok() {
        List<FruitRecord> recordList = new ArrayList<>();
        recordList.add(bananaRecord);
        recordList.add(null);
        operator.doAllOperation(recordList, daoService);
    }

    @Test(expected = RuntimeException.class)
    public void doAllOperation_nullFruitDaoService_Ok() {
        List<FruitRecord> recordList = new ArrayList<>();
        recordList.add(bananaRecord);
        operator.doAllOperation(recordList, null);
    }

    @Test(expected = RuntimeException.class)
    public void doAllOperation_nullBothParam_Ok() {
        operator.doAllOperation(null, null);
    }
}
