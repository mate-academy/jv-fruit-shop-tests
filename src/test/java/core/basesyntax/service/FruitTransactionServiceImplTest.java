package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.cvswork.FileReader;
import core.basesyntax.cvswork.FileReaderImpl;
import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.BalanceOperation;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperation;
import core.basesyntax.service.operation.ReturnOperation;
import core.basesyntax.service.operation.SupplyOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitTransactionServiceImplTest {
    private static FruitTransactionService fruitTransactionService;
    private static List<FruitTransaction> transactionList;
    private static List<FruitTransaction> emptyFile;
    private static List<FruitTransaction> withoutName;
    private static FruitDao fruitDao;
    private static FileReader read;
    private static FruitTransactionParser lineSeparator;

    @BeforeAll
    public static void beforeAll() {
        read = new FileReaderImpl();
        lineSeparator = new FruitTransactionParserImpl();
        fruitDao = new FruitDaoImpl();
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        TransactionStrategy strategy = new TransactionStrategyImpl(operationHandlerMap);
        fruitTransactionService = new FruitTransactionServiceImpl(strategy, fruitDao);
        read = new FileReaderImpl();
        lineSeparator = new FruitTransactionParserImpl();
    }

    @BeforeEach
    public void setUp() {
        emptyFile = lineSeparator.parse(
                read.read("src/test/java/core/basesyntax/resourse/emptyLine.cvs"));
        withoutName = lineSeparator.parse(
                read.read("src/test/java/core/basesyntax/resourse/withoutName.cvs"));
        Storage.fruits.clear();
        transactionList = new ArrayList<>();
        transactionList.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "guava", 20));
        transactionList.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "cherry", 100));
        transactionList.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "guava", 100));
        transactionList.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "guava", 13));
        transactionList.add(new FruitTransaction(FruitTransaction.Operation.RETURN,
                "cherry", 10));
        transactionList.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "cherry", 20));
        transactionList.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "guava", 5));
        transactionList.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "guava", 50));
    }

    @Test
    public void manipulationData_correctData_ok() {
        List<Fruit> expectedData = new ArrayList<>();
        expectedData.add(new Fruit(90, "cherry"));
        expectedData.add(new Fruit(152, "guava"));
        List<Fruit> actualData = Storage.fruits;
        fruitTransactionService.process(transactionList);
        assertEquals(expectedData.toString(), actualData.toString());
    }

    @Test
    public void manipulationData_withName_Ok() {
        List<Fruit> expectedData = new ArrayList<>();
        expectedData.add(new Fruit(20, "guava"));
        expectedData.add(new Fruit(100, "cherry"));
        List<Fruit> actualData = Storage.fruits;
        fruitTransactionService.process(withoutName);
        assertEquals(expectedData.toString(), actualData.toString());
    }

    @Test
    public void manipulationData_withEmptyList_Ok() {
        List<Fruit> expectedData = new ArrayList<>();
        List<Fruit> actualData = Storage.fruits;
        fruitTransactionService.process(emptyFile);
        assertEquals(expectedData.toString(), actualData.toString());
    }
}
