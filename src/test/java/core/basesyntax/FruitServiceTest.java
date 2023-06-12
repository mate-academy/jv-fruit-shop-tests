package core.basesyntax;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.impl.FruitServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FruitServiceTest {
    private FruitService fruitService;
    private FruitStorage fruitStorage;

    @Before
    public void setUp() {
        fruitStorage = new FruitStorage();
        fruitService = new FruitServiceImpl(fruitStorage);
    }

    @Test
    public void testProcessTransactions_BalanceOperation_AddsFruitToStorage() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "Apple", 10);
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        fruitService.processTransactions(transactions);

        Fruit storedFruit = fruitStorage.getFruit("Apple");
        Assert.assertNotNull(storedFruit);
        Assert.assertEquals("Apple", storedFruit.getName());
        Assert.assertEquals(10, storedFruit.getQuantity());
    }

    @Test
    public void testProcessTransactions_PurchaseOperation_ReducesFruitQuantity() {
        Fruit initialFruit = new Fruit("Apple", 20);
        fruitStorage.addFruit(initialFruit);

        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "Apple", 5);
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        fruitService.processTransactions(transactions);

        Fruit updatedFruit = fruitStorage.getFruit("Apple");
        Assert.assertNotNull(updatedFruit);
        Assert.assertEquals("Apple", updatedFruit.getName());
        Assert.assertEquals(15, updatedFruit.getQuantity());
    }

    @Test
    public void testProcessTransactions_ReturnOperation_IncreasesFruitQuantity() {
        Fruit initialFruit = new Fruit("Apple", 10);
        fruitStorage.addFruit(initialFruit);

        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "Apple", 5);
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        fruitService.processTransactions(transactions);

        Fruit updatedFruit = fruitStorage.getFruit("Apple");
        Assert.assertNotNull(updatedFruit);
        Assert.assertEquals("Apple", updatedFruit.getName());
        Assert.assertEquals(15, updatedFruit.getQuantity());
    }

    @Test
    public void testProcessTransactions_SupplyOperation_AddsToExistingFruitQuantity() {
        Fruit initialFruit = new Fruit("Apple", 10);
        fruitStorage.addFruit(initialFruit);

        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "Apple", 5);
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        fruitService.processTransactions(transactions);

        Fruit updatedFruit = fruitStorage.getFruit("Apple");
        Assert.assertNotNull(updatedFruit);
        Assert.assertEquals("Apple", updatedFruit.getName());
        Assert.assertEquals(15, updatedFruit.getQuantity());
    }

    @Test
    public void testGenerateReport_ReturnsCorrectReportData() {
        Fruit fruit1 = new Fruit("Apple", 10);
        Fruit fruit2 = new Fruit("Banana", 5);
        fruitStorage.addFruit(fruit1);
        fruitStorage.addFruit(fruit2);

        List<String> expectedReportData = new ArrayList<>();
        expectedReportData.add("fruit,quantity");
        expectedReportData.add("Apple,10");
        expectedReportData.add("Banana,5");

        List<String> reportData = fruitService.generateReport();

        Assert.assertEquals(expectedReportData, reportData);
    }

    @Test
    public void testProcessTransactions_NullFruit_ReturnsErrorMessage() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                null, 10);
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        fruitService.processTransactions(transactions);

        Fruit storedFruit = fruitStorage.getFruit(null);
        Assert.assertNull(storedFruit);
        Assert.assertTrue(fruitService.getErrorMessages().contains("Invalid fruit name: null"));
    }

    @Test
    public void testProcessTransactions_NegativeQuantity_ReturnsErrorMessage() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "Apple", -5);
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        fruitService.processTransactions(transactions);

        Fruit storedFruit = fruitStorage.getFruit("Apple");
        Assert.assertNull(storedFruit);
        Assert.assertTrue(fruitService.getErrorMessages().contains("Invalid fruit quantity: -5"));
    }
}
