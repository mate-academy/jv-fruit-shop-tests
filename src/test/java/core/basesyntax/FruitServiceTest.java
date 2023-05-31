package core.basesyntax;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataProcessService;
import core.basesyntax.service.DataReaderService;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.impl.DataProcessServiceImpl;
import core.basesyntax.service.impl.DataReaderServiceImpl;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.service.operation.BalanceOperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandler;
import core.basesyntax.service.operation.ReturnOperationHandler;
import core.basesyntax.service.operation.SupplyOperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceTest {
    private static final String PATH_TO_FIRST_TEST_FILE = "src/test/resources/inputDataFile.csv";
    private static FruitDao fruitDao;
    private static FruitService fruitService;
    private static DataProcessService dataProcessService;
    private static DataReaderService dataReaderService;
    private static Map<FruitTransaction.Operation, OperationHandler> fruitTransactionMap;
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        dataProcessService = new DataProcessServiceImpl();
        dataReaderService = new DataReaderServiceImpl();
        fruitTransactionMap = new HashMap<>();
        operationStrategy = new OperationStrategyImpl(mapGenerator());
        fruitService = new FruitServiceImpl(operationStrategy,
                dataReaderService, dataProcessService);
    }

    @Test
    public void calculateFruits_validInput_ok() {
        List<Fruit> expectedListOfFruitsInShop = new ArrayList<>(List.of(
                new Fruit("orange", 17),
                new Fruit("pineapple", 55),
                new Fruit("apple", 20)));
        fruitService.calculateFruits(PATH_TO_FIRST_TEST_FILE);
        Assert.assertEquals("Incorrect result of FruitList in the storage. Result should be: ",
                expectedListOfFruitsInShop, FruitStorage.fruitsInShop);
        Assert.assertEquals("Incorrect size of FruitList in the storage. Result should be: ",
                expectedListOfFruitsInShop.size(), FruitStorage.fruitsInShop.size());
    }

    @After
    public void tearDown() {
        FruitStorage.fruitsInShop.clear();
    }

    private static Map<FruitTransaction.Operation, OperationHandler> mapGenerator() {
        fruitTransactionMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(fruitDao));
        fruitTransactionMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(fruitDao));
        fruitTransactionMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(fruitDao));
        fruitTransactionMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(fruitDao));
        return fruitTransactionMap;
    }
}
