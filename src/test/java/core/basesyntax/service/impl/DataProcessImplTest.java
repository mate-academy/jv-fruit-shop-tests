package core.basesyntax.service.impl;

import static junit.framework.TestCase.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHendler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataProcessImplTest {
    private static List<FruitTransaction> listFruitTransaction;
    private static Map<FruitTransaction.Operation,
            OperationHendler> operationHendlerMap;
    private FruitDao fruitDao;

    @BeforeClass
    public static void beforeClass() {
        operationHendlerMap = new HashMap<>();
        operationHendlerMap.put(FruitTransaction.Operation.BALANCE,
                new OperationHendlerBalance());
        operationHendlerMap.put(FruitTransaction.Operation.PURCHASE,
                new OperationHendlerPurchase());
        operationHendlerMap.put(FruitTransaction.Operation.RETURN,
                new OperationHendlerReturn());
        operationHendlerMap.put(FruitTransaction.Operation.SUPPLY,
                new OperationHendlerSupply());

        FruitTransaction firstFruitTransaction = new FruitTransaction("apple", 100);
        firstFruitTransaction.setOperationByIndex("b");
        FruitTransaction secondFruitransaction = new FruitTransaction("apple", 20);
        secondFruitransaction.setOperationByIndex("s");
        FruitTransaction thirdFruitransaction = new FruitTransaction("apple", 10);
        thirdFruitransaction.setOperationByIndex("r");
        FruitTransaction fourthFruitransaction = new FruitTransaction("apple", 20);
        fourthFruitransaction.setOperationByIndex("p");
        listFruitTransaction = List.of(firstFruitTransaction, secondFruitransaction,
                thirdFruitransaction, fourthFruitransaction);
    }

    @Before
    public void setUp() {
        fruitDao = new FruitDaoImpl();
    }

    @Test
    public void processingData_Ok() {
        int expected = 110;

        DataProcessImpl dataProcess = new DataProcessImpl(fruitDao, operationHendlerMap);
        dataProcess.processingData(listFruitTransaction);
        Integer actual = Storage.storage.get("apple");

        assertEquals(expected, (long) actual);
    }
}
