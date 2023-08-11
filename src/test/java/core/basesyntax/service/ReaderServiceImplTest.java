package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static final String PATH_TEST = "src/test/resources/";
    private static final String FILE_NAME = "fruits.csv";
    private static final String FILE_EMPTY = "fruits_empty.csv";
    private static final String FILE_NO_DATA = "fruits_no_data.csv";
    private static final String FILE_BAD_OPERATION = "fruits_bad_operation.csv";
    private static final String FILE_BAD_NUMBER = "fruits_bad_number.csv";
    private static final String FILE_STORAGE = "fruits_storage.csv";
    private ReaderService readerService;

    @BeforeEach
    void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @AfterEach
    void afterEachTest() {
        Storage.fruitTransactions.clear();
    }

    @Test
    void readFromFile_test_OK() {
        //File testFile = new File(PATH_TEST + FILE_NAME);
        //if (!testFile.exists()) {
        //    System.out.println("In folder " + PATH_TEST + " not found file " + FILE_NAME);
        //    return;
        //}
        List<FruitTransaction> expected = createFruits_OK();
        List<FruitTransaction> result = readerService.readFromFile(
                PATH_TEST + FILE_NAME);
        Assert.assertEquals("File " + PATH_TEST + FILE_NAME
                + " not read correctly must be: " + expected + "\n", expected, result);
    }

    @Test
    void readFromFile_empty_NotOK() {
        List<FruitTransaction> expected = new ArrayList<>();
        List<FruitTransaction> result = readerService.readFromFile(
                PATH_TEST + FILE_EMPTY);
        Assert.assertEquals("File " + PATH_TEST + FILE_EMPTY
                + " not read correctly must be: " + expected + "\n", expected, result);
    }

    @Test
    void readFromFile_noData_NotOK() {
        List<FruitTransaction> expected = new ArrayList<>();
        List<FruitTransaction> result = readerService.readFromFile(
                PATH_TEST + FILE_NO_DATA);
        Assert.assertEquals("File " + PATH_TEST + FILE_NO_DATA
                + " not read correctly must be: " + expected + "\n", expected, result);
    }

    @Test
    void readFromFile_badOperation_NotOK() {
        Throwable thrown = Assert.assertThrows(RuntimeException.class, () -> {
            readerService.readFromFile(PATH_TEST + FILE_BAD_OPERATION);
        });
        Assert.assertNotNull(thrown.getMessage());
    }

    @Test
    void readFromFile_badNumber_NotOK() {
        Throwable thrown = Assert.assertThrows(RuntimeException.class, () -> {
            readerService.readFromFile(PATH_TEST + FILE_BAD_NUMBER);
        });
        Assert.assertNotNull(thrown.getMessage());
    }

    @Test
    void readFromFile_addInStorage_OK() {
        List<FruitTransaction> expected = readerService
                .readFromFile(PATH_TEST + FILE_STORAGE);
        List<FruitTransaction> result = Storage.fruitTransactions.stream()
                .collect(Collectors.toList());
        Assert.assertEquals("Wrong data written to storage must be: "
                + expected + "\n", expected, result);
    }

    public List<FruitTransaction> createFruits_OK() {
        FruitTransaction fruitTransaction1 = new FruitTransaction();
        fruitTransaction1.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction1.setFruit("apple");
        fruitTransaction1.setQuantity(100);
        List<FruitTransaction> fruitsList = new ArrayList<>();
        fruitsList.add(fruitTransaction1);
        FruitTransaction fruitTransaction2 = new FruitTransaction();
        fruitTransaction2.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction2.setFruit("apple");
        fruitTransaction2.setQuantity(10);
        fruitsList.add(fruitTransaction2);
        FruitTransaction fruitTransaction3 = new FruitTransaction();
        fruitTransaction3.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction3.setFruit("apple");
        fruitTransaction3.setQuantity(20);
        fruitsList.add(fruitTransaction3);
        return fruitsList;
    }
}
