package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.OperationType;
import core.basesyntax.service.FruitService;
import core.basesyntax.strategy.AddOperationHandler;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceImplTest {
    private static FruitService fruitService;

    @BeforeClass
    public static void beforeClass() {
        Map<OperationType, OperationHandler> testMap = new HashMap<>();
        testMap.put(OperationType.BALANCE, new BalanceOperationHandler());
        testMap.put(OperationType.PURCHASE, new PurchaseOperationHandler());
        testMap.put(OperationType.RETURN, new AddOperationHandler());
        testMap.put(OperationType.SUPPLY, new AddOperationHandler());
        fruitService = new FruitServiceImpl(testMap);
    }

    @Before
    public void setUp() {
        Storage.getFruits().clear();
    }

    @Test
    public void callMethodOnEmptyListName_Ok() {
        List<Transaction> testList = new ArrayList<>();
        fruitService.applyOperations(testList);
    }
}
