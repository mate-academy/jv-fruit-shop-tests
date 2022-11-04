package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
import org.junit.Before;
import org.junit.Test;

public class ParseFruitTransactionTest {
    private final Map<Operation, OperationHandler> operationsMap = new HashMap<>();
    private final ParseFruitTransaction parseFruitTransaction = new ParseFruitTransactionImpl();

    @Before
    public void createOperationMap() {
        operationsMap.put(Operation.RETURN, new OperationHandlerReturn());
        operationsMap.put(Operation.SUPPLY, new OperationHandlerSupply());
        operationsMap.put(Operation.PURCHASE, new OperationHandlerPurchase());
        operationsMap.put(Operation.BALANCE, new OperationHandlerBalance());
    }

    @Test
    public void parseFruitTransactionTest_Ok() {
        List<String> inputData = new ArrayList<>();
        inputData.add("type,fruit,quantity");
        inputData.add("b,banana,20");
        inputData.add("b,apple,100");
        inputData.add("b,orange,150");
        inputData.add("s,banana,100");
        inputData.add("r,orange,20");
        inputData.add("p,banana,13");
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 107);
        expected.put("apple", 100);
        expected.put("orange", 170);
        FruitDao fruitDao = new FruitDaoImpl();
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationsMap);
        parseFruitTransaction.parse(inputData, operationStrategy);
        Map<String, Integer> actual = fruitDao.getStorage();
        assertEquals(expected, actual);
    }

    @Test
    public void parseIncorrectOperationTest_NotOk() {
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationsMap);
        List<String> inputData = new ArrayList<>();
        inputData.add("type,fruit,quantity");
        inputData.add("1,banana,20");
        inputData.add("%,apple,100");
        inputData.add("/,orange,150");
        try {
            parseFruitTransaction.parse(inputData, operationStrategy);
        } catch (RuntimeException e) {
            return;
        }
        fail("Test failed! Method should be thrown runtime exception: "
                + "\"operation + 'operation' +\" not exist");
    }
}
