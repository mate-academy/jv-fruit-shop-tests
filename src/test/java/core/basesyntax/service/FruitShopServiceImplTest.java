package core.basesyntax.service;

import core.basesyntax.dao.csv.CsvFileHandlerDao;
import core.basesyntax.dao.csv.impl.CsvFileHandlerDaoImpl;
import core.basesyntax.dao.storage.FruitStorageDao;
import core.basesyntax.dao.storage.impl.FruitStorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.impl.BalanceOperationHandler;
import core.basesyntax.service.operation.impl.PurchaseOperationHandler;
import core.basesyntax.service.operation.impl.ReturnOperationHandler;
import core.basesyntax.service.operation.impl.SupplyOperationHandler;
import core.basesyntax.strategy.FruitShopStrategy;
import core.basesyntax.strategy.impl.FruitShopStrategyImpl;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitShopServiceImplTest {
    private static final String ALREADY_EXIST_EXCEPTION = "Already exist";
    private static final String NOT_ENOUGH_FRUITS_EXCEPTION = "There is not enough fruits!";
    private static final String READ_FILE_PATH = "src/test/java/core/basesyntax/csv/testDB.csv";
    private static final String WRITE_FILE_PATH =
            "src/test/java/core/basesyntax/csv/testReport.csv";
    private static final long SKIP_FIRST_ROW = 1L;
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final String SPLIT_SYMBOL = ",";
    private static final String FIRST_ROW_IN_REPORT = "fruit,quantity";
    private static final int FIRST_ROW_INDEX = 0;

    private static FruitShopServiceImpl underTest;
    private static FruitStorageDao fruitStorageDao;
    private static CsvFileHandlerDao csvFileHandlerDao;
    private static FruitShopStrategy fruitShopStrategy;
    private static Storage storage;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private static List<FruitTransaction> fruitTransactionList;

    @BeforeEach
    public void setUp() {
        storage = new Storage();
        fruitStorageDao = new FruitStorageDaoImpl(storage);
        csvFileHandlerDao = new CsvFileHandlerDaoImpl();
        underTest = new FruitShopServiceImpl(fruitStorageDao, csvFileHandlerDao);
        underTest = new FruitShopServiceImpl(fruitStorageDao, csvFileHandlerDao);
        operationHandlerMap = Map.of(
            FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(underTest),
            FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler(underTest),
            FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(underTest),
            FruitTransaction.Operation.RETURN, new ReturnOperationHandler(underTest));
        fruitShopStrategy = new FruitShopStrategyImpl(operationHandlerMap);
        fruitTransactionList = underTest.readAllFromCsv(READ_FILE_PATH);
        fruitStorageDao = new FruitStorageDaoImpl(storage);
    }

    @Test
    void readAllFromCsv() {
        List<FruitTransaction> expected = csvFileHandlerDao.readCsv(READ_FILE_PATH)
                .stream()
                .skip(SKIP_FIRST_ROW)
                .map(this::mapToFruitTransaction)
                .collect(Collectors.toList());
        List<FruitTransaction> actual = underTest.readAllFromCsv(READ_FILE_PATH);
        Assertions.assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    void exportReport() {
        startWork(fruitTransactionList, fruitShopStrategy);
        underTest.exportReport(WRITE_FILE_PATH);
        List<String> actual = csvFileHandlerDao.readCsv(WRITE_FILE_PATH);
        List<String> expected = underTest.generateReport(fruitStorageDao.getAll());
        Assertions.assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    void balanceSuccess() {
        String fruit = "banana";
        int quantity = 152;
        int actual = underTest.balance(fruit, quantity);
        Assertions.assertEquals(quantity, actual);
    }

    @Test
    void balanceException() {
        String fruit = "banana";
        int quantity = 152;
        int actual = fruitStorageDao.addFruitQuantity(fruit, quantity);
        Assertions.assertEquals(quantity, actual);
        Exception exception = Assertions.assertThrows(RuntimeException.class,
                () -> underTest.balance(fruit, quantity));
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(ALREADY_EXIST_EXCEPTION));
    }

    @Test
    void supplySuccess() {
        startWork(fruitTransactionList, fruitShopStrategy);
        String fruit = "banana";
        int quantity = 152;
        int existing = fruitStorageDao.getFruitQuantity(fruit);
        int expected = quantity + existing;
        int actual = underTest.supply(fruit, quantity);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void supplySuccessEmptyStorage() {
        String fruit = "banana";
        int quantity = 152;
        int actual = underTest.supply(fruit, quantity);
        Assertions.assertEquals(quantity, actual);
    }

    @Test
    void purchaseSuccess() {
        startWork(fruitTransactionList, fruitShopStrategy);
        String fruit = "banana";
        int quantity = 152;
        int existing = fruitStorageDao.getFruitQuantity(fruit);
        int expected = existing - quantity;
        int actual = underTest.purchase(fruit, quantity);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void purchaseException() {
        startWork(fruitTransactionList, fruitShopStrategy);
        String fruit = "banana";
        int quantity = 1520;
        Exception exception = Assertions.assertThrows(RuntimeException.class,
                () -> underTest.purchase(fruit, quantity));
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(NOT_ENOUGH_FRUITS_EXCEPTION));
    }

    @Test
    void returnFruitsSuccess() {
        String fruit = "banana";
        int quantity = 152;
        int actual = underTest.returnFruits(fruit, quantity);
        Assertions.assertEquals(quantity, actual);
    }

    @Test
    void returnFruitsSuccessEmptyStorage() {
        String fruit = "banana";
        int quantity = 152;
        int actual = underTest.returnFruits(fruit, quantity);
        Assertions.assertEquals(quantity, actual);
    }

    private FruitTransaction mapToFruitTransaction(String row) {
        String[] strings = row.split(SPLIT_SYMBOL);
        return FruitTransaction
            .builder()
            .operation(FruitTransaction.Operation.byCode(strings[OPERATION_INDEX]))
            .fruit(strings[FRUIT_INDEX])
            .quantity(Integer.parseInt(strings[QUANTITY_INDEX]))
            .build();
    }

    private void startWork(List<FruitTransaction> fruitTransactionList,
                           FruitShopStrategy fruitShopStrategy) {
        fruitTransactionList.forEach(fruitTransaction -> fruitShopStrategy
                .get(fruitTransaction.getOperation())
                .operation(fruitTransaction.getFruit(), fruitTransaction.getQuantity()));
    }
}
