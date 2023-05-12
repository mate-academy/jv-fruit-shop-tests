package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class WarehouseDaoReaderTest {
    public static final String PATH_TEST_FILE = "src/test/resources/input.csv";
    public static final String PATH_FILE_NOT_EXIST = "src/test/resources/notexist.csv";
    public static final String PATH_FILE_INVALID_FORMAT = "src/test/resources/invalidFormat.csv";
    private static final WarehouseDaoReader warehouseDaoReader = new WarehouseDaoReader();

    @Test
    void readData_validFile_ok() {
        List<FruitTransaction> fruitTransactionListExpected = new ArrayList<>();
        fruitTransactionListExpected.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 20));
        fruitTransactionListExpected.add(new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 100));
        fruitTransactionListExpected.add(new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 13));
        fruitTransactionListExpected.add(new FruitTransaction(
                FruitTransaction.Operation.RETURN, "banana", 10));
        List<FruitTransaction> actual = warehouseDaoReader.readData(PATH_TEST_FILE);
        assertEquals(fruitTransactionListExpected, actual);
    }

    @Test
    void readData_invalidFormat_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> warehouseDaoReader.readData(PATH_FILE_INVALID_FORMAT));
    }

    @Test
    void readData_null_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> warehouseDaoReader.readData(null));
    }

    @Test
    void readData_fileNotExist_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> warehouseDaoReader.readData(PATH_FILE_NOT_EXIST));
    }

}
