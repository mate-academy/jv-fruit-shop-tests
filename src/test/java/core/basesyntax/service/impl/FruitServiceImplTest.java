package core.basesyntax.service.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.operation.BalanceOperationHandler;
import core.basesyntax.strategy.operation.OperationHandler;
import core.basesyntax.strategy.operation.PurchaseOperationHandler;
import core.basesyntax.strategy.operation.ReturnOperationHandler;
import core.basesyntax.strategy.operation.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceImplTest {
    private static final int TEST_FRUIT_AMOUNT_ONE = 10;
    private static final int TEST_FRUIT_AMOUNT_TWO = 20;
    private static final String TEST_FRUIT_TYPE_ONE = "avocado";
    private static final String TEST_FRUIT_TYPE_TWO = "papaya";
    private static final FruitTransaction.Operation BALANCE
            = FruitTransaction.Operation.BALANCE;
    private static FruitService fruitService;
    private static FruitDao fruitDao;
    private static FruitTransaction fruitTransactionOne;
    private static FruitTransaction fruitTransactionTwo;
    private static OperationStrategy operationStrategy;
    private static List<FruitTransaction> fruitsTransactionListEmpty;
    private static List<FruitTransaction> testTransactionList;
    private static Map<FruitTransaction.Operation,
            OperationHandler> fruitServiceMap = new HashMap<>();

    static {
        fruitServiceMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        fruitServiceMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        fruitServiceMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        fruitServiceMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler());
    }

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        operationStrategy = new OperationStrategyImpl(fruitServiceMap);
        fruitService = new FruitServiceImpl(fruitDao, operationStrategy);
        fruitsTransactionListEmpty = new ArrayList<>();
        FruitTransaction fruitTransactionOne = new FruitTransaction();
        fruitTransactionOne.setFruitType(TEST_FRUIT_TYPE_ONE);
        fruitTransactionOne.setAmount(TEST_FRUIT_AMOUNT_ONE);
        fruitTransactionOne.setOperation(BALANCE);
        FruitTransaction fruitTransactionTwo = new FruitTransaction();
        fruitTransactionTwo.setFruitType(TEST_FRUIT_TYPE_TWO);
        fruitTransactionTwo.setAmount(TEST_FRUIT_AMOUNT_TWO);
        fruitTransactionTwo.setOperation(BALANCE);
        testTransactionList = new ArrayList<>();
        testTransactionList.add(fruitTransactionOne);
        testTransactionList.add(fruitTransactionTwo);
    }

    @Test(expected = RuntimeException.class)
    public void setDataToStorage_listEmpty_notOk() {
        fruitService.setDataToStorage(fruitsTransactionListEmpty);
    }

    @Test
    public void setDataToStorage_listValid_ok() {
        boolean hasOperationHandler = fruitService.setDataToStorage(testTransactionList);
        assertTrue(hasOperationHandler);
    }
}
