package core.basesyntax.service.storageservice;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.handlers.OperationDecreaseHandler;
import core.basesyntax.handlers.OperationHandler;
import core.basesyntax.handlers.OperationIncreaseHandler;
import core.basesyntax.model.dto.TransactionDto;
import core.basesyntax.model.operations.Operation;
import core.basesyntax.model.product.Fruit;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class StorageServiceImplTest {
    private static StorageService storageService;
    private static StorageDao storageDao;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
        Map<String, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put("b", new OperationIncreaseHandler(storageDao));
        operationHandlerMap.put("s", new OperationIncreaseHandler(storageDao));
        operationHandlerMap.put("p", new OperationDecreaseHandler(storageDao));
        operationHandlerMap.put("r", new OperationIncreaseHandler(storageDao));
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        storageService = new StorageServiceImpl(operationStrategy,storageDao);
    }

    @Test
    public void addToStorage_isOkay() {
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("apple"), 30);
        expected.put(new Fruit("banana"), 15);
        List<TransactionDto> transactionDtoList = List.of(
                new TransactionDto(Operation.S, new Fruit("apple"), 30),
                new TransactionDto(Operation.B, new Fruit("banana"), 30),
                new TransactionDto(Operation.P, new Fruit("banana"), 15));
        storageService.addToStorage(transactionDtoList);
        Map<Fruit, Integer> actual = storageDao.getAll();
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void addToStorage_listIsNull_notOk() {
        storageService.addToStorage(null);
    }

    @Test
    public void addToStorage_listIsEmpty_isOk() {
        Map<Fruit, Integer> expected = new HashMap<>();
        List<TransactionDto> transactionDtoList = new ArrayList<>();
        storageService.addToStorage(transactionDtoList);
        Map<Fruit, Integer> actual = storageDao.getAll();
        assertEquals(expected, actual);
    }

    @Test
    public void getReport_isOk() {
        storageDao.add(new Fruit("banana"), 30);
        storageDao.add(new Fruit("apple"), 15);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,30" + System.lineSeparator()
                + "apple,15" + System.lineSeparator();
        String actual = storageService.getReport();
        assertEquals(expected, actual);
    }

    @Test
    public void getReport_EmptyStorage_isOk() {
        String expected = "fruit,quantity" + System.lineSeparator();
        String actual = storageService.getReport();
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        storageDao.getAll().clear();
    }
}
