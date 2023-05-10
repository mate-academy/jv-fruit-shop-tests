package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.FruitTransaction;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class WarehouseDaoReaderTest {
    public static final String PATH_TEST_FILE = "src/test/resources/input.csv";
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
        String testFileBody = "type,fruit,quantity" + System.lineSeparator()
                + "b,banana,20" + System.lineSeparator()
                + "s,banana,100" + System.lineSeparator()
                + "p,banana,13" + System.lineSeparator()
                + "r,banana,10";
        try {
            Files.writeString(Path.of(PATH_TEST_FILE), testFileBody, StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RuntimeException("Can't create a file " + PATH_TEST_FILE, e);
        }
        List<FruitTransaction> actual = warehouseDaoReader.readData(PATH_TEST_FILE);
        assertEquals(fruitTransactionListExpected, actual);
    }

    @Test
    void readData_invalidFormat_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> warehouseDaoReader.readData(PATH_TEST_FILE.replace("csv", "txt")));
    }

    @Test
    void readData_null_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> warehouseDaoReader.readData(null));
    }

    @Test
    void readData_fileNotExist_notOk() {
        String testFileBody = "type,fruit" + System.lineSeparator()
                + "b,banana" + System.lineSeparator()
                + "s,banana" + System.lineSeparator()
                + "p,banana" + System.lineSeparator()
                + "r,banana";
        try {
            Files.writeString(Path.of(PATH_TEST_FILE), testFileBody, StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RuntimeException("Can't create a file " + PATH_TEST_FILE, e);
        }
        Assertions.assertThrows(RuntimeException.class,
                () -> warehouseDaoReader.readData(PATH_TEST_FILE));
    }

}
