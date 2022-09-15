package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.impl.FruitShopServiceImpl;
import core.basesyntax.strategy.OperationProcessor;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.AddOperationProcessor;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.SubstractOperationProcessor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceTest {
    private static final String SUPPLY = "s";
    private static final String PURCHASE = "p";
    private static final String APPLE = "apple";
    private static FruitShopService fruitShopService;
    private static OperationStrategy operationStrategy;
    private static Transaction.Operation supplyOperation;
    private static Transaction.Operation purchaseOperation;
    private static OperationProcessor addOperationProcessor;
    private static OperationProcessor substractOperationProcessor;
    private static FruitDao fruitDao;
    private static Map<Transaction.Operation, OperationProcessor> operationProcessMap;
    private static List<Transaction> transactionList;

    @BeforeClass
    public static void before() {
        transactionList = new ArrayList<>();
        fruitDao = new FruitDaoImpl();
        operationProcessMap = new HashMap<>();
        addOperationProcessor = new AddOperationProcessor(fruitDao);
        substractOperationProcessor = new SubstractOperationProcessor(fruitDao);
        supplyOperation = Transaction.Operation.getByCode(SUPPLY);
        purchaseOperation = Transaction.Operation.getByCode(PURCHASE);
        operationProcessMap.put(supplyOperation, addOperationProcessor);
        operationProcessMap.put(purchaseOperation, substractOperationProcessor);
        operationStrategy = new OperationStrategyImpl(operationProcessMap);
        fruitShopService = new FruitShopServiceImpl(operationStrategy);

    }

    @Before
    public void beforeEach() {
        Storage.fruitStorage.clear();
    }

    @Test
    public void runTransaction_Ok() {
        final Integer expectedApple = 5;
        transactionList.add(new Transaction(supplyOperation, APPLE, 15));
        transactionList.add(new Transaction(purchaseOperation, APPLE, 10));
        fruitShopService.runTransaction(transactionList);
        assertEquals(expectedApple, Storage.fruitStorage.get(APPLE));
    }
}
