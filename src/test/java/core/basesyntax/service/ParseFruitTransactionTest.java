package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.Operation;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.OperationStrategy;
import core.basesyntax.service.operation.impl.OperationHandlerBalance;
import core.basesyntax.service.operation.impl.OperationHandlerPurchase;
import core.basesyntax.service.operation.impl.OperationHandlerReturn;
import core.basesyntax.service.operation.impl.OperationHandlerSupply;
import core.basesyntax.service.operation.impl.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParseFruitTransactionTest {
    private static OperationStrategy operationStrategy;
    private static FruitDao fruitDao;
    private final ParseFruitTransaction parseFruitTransaction = new ParseFruitTransactionImpl();

    @BeforeClass
    public static void setUp() {
        Map<Operation, OperationHandler> operationsMap = new HashMap<>();
        operationsMap.put(Operation.RETURN, new OperationHandlerReturn());
        operationsMap.put(Operation.SUPPLY, new OperationHandlerSupply());
        operationsMap.put(Operation.PURCHASE, new OperationHandlerPurchase());
        operationsMap.put(Operation.BALANCE, new OperationHandlerBalance());
        operationStrategy = new OperationStrategyImpl(operationsMap);
        fruitDao = new FruitDaoImpl();
        operationStrategy = new OperationStrategyImpl(operationsMap);
    }

    @Test
    public void parse_fruitTransactionTest_Ok() {
        List<String> correctInputData = new ArrayList<>();
        correctInputData.add("type,fruit,quantity");
        correctInputData.add("b,banana,20");
        correctInputData.add("b,apple,100");
        correctInputData.add("b,orange,150");
        correctInputData.add("s,banana,100");
        correctInputData.add("r,orange,20");
        correctInputData.add("p,banana,13");
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 107);
        expected.put("apple", 100);
        expected.put("orange", 170);
        parseFruitTransaction.parse(correctInputData, operationStrategy);
        Map<String, Integer> actual = fruitDao.getStorage();
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parse_incorrectOperationTest_notOk() {
        List<String> incorrectOperationInputData = new ArrayList<>();
        incorrectOperationInputData.add("type,fruit,quantity");
        incorrectOperationInputData.add("1,banana,20");
        incorrectOperationInputData.add("%,apple,100");
        incorrectOperationInputData.add("/,orange,150");
        parseFruitTransaction.parse(incorrectOperationInputData, operationStrategy);
    }
}

