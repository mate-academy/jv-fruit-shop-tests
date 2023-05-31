package core.basesyntax.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import core.basesyntax.dao.CsvFileReader;
import core.basesyntax.dao.CsvFileReaderImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileReaderImplTest {
    private static final String TEST_FILE = "src/test/resources/fromFileTest.csv";
    private static CsvFileReader csvFileReader;

    @BeforeClass
    public static void setUp() {
        csvFileReader = new CsvFileReaderImpl();
    }

    @Test(expected = RuntimeException.class)
    public void readTransactions_noValidPath_notOk() {
        String noValidPath = "ndbkam.sxc";
        csvFileReader.readTransactions(noValidPath);
    }

    @Test
    public void readTransactions_validData_ok() {
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(balanceBananaFruitTransaction());
        fruitTransactionList.add(balanceAppleFruitTransaction());
        fruitTransactionList.add(supplyBananaFruitTransaction());
        fruitTransactionList.add(purchaseBananaFruitTransaction());
        fruitTransactionList.add(returnAppleFruitTransaction());
        String expected = fruitTransactionList.stream()
                .map(Object::toString)
                .collect(Collectors.joining(System.lineSeparator()));
        String actual
                = csvFileReader.readTransactions(TEST_FILE).trim();
        assertNotNull(actual);
        assertFalse(actual.isEmpty());
    }

    private FruitTransaction balanceBananaFruitTransaction() {
        FruitTransaction balanceBanana = new FruitTransaction();
        balanceBanana.setOperation(Operation.BALANCE);
        balanceBanana.setFruit("banana");
        balanceBanana.setQuantity(20);
        return balanceBanana;
    }

    private FruitTransaction balanceAppleFruitTransaction() {
        FruitTransaction balanceApple = new FruitTransaction();
        balanceApple.setOperation(Operation.BALANCE);
        balanceApple.setFruit("apple");
        balanceApple.setQuantity(100);
        return balanceApple;
    }

    private FruitTransaction supplyBananaFruitTransaction() {
        FruitTransaction supplyBanana = new FruitTransaction();
        supplyBanana.setOperation(Operation.SUPPLY);
        supplyBanana.setFruit("banana");
        supplyBanana.setQuantity(100);
        return supplyBanana;
    }

    private FruitTransaction purchaseBananaFruitTransaction() {
        FruitTransaction purchaseBanana = new FruitTransaction();
        purchaseBanana.setOperation(Operation.PURCHASE);
        purchaseBanana.setFruit("banana");
        purchaseBanana.setQuantity(13);
        return purchaseBanana;
    }

    private FruitTransaction returnAppleFruitTransaction() {
        FruitTransaction returnApple = new FruitTransaction();
        returnApple.setOperation(Operation.RETURN);
        returnApple.setFruit("apple");
        returnApple.setQuantity(10);
        return returnApple;
    }
}
