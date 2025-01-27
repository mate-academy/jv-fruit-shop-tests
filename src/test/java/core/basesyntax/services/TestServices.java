package core.basesyntax.services;

import core.basesyntax.ActivityStrategyImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.models.FruitTransaction;
import core.basesyntax.models.activities.ActivityHandler;
import core.basesyntax.models.activities.BalanceActivityHandler;
import core.basesyntax.models.activities.PurchaseActivityHandler;
import core.basesyntax.models.activities.ReturnActivityHandler;
import core.basesyntax.models.activities.SupplyActivityHandler;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class TestServices {

    private final Map<FruitTransaction.TypeOfActivity, ActivityHandler> activityHandlerMap = Map.of(
            FruitTransaction.TypeOfActivity.BALANCE, new BalanceActivityHandler(),
            FruitTransaction.TypeOfActivity.SUPPLY, new SupplyActivityHandler(),
            FruitTransaction.TypeOfActivity.PURCHASE, new PurchaseActivityHandler(),
            FruitTransaction.TypeOfActivity.RETURN, new ReturnActivityHandler()
    );

    @Test
    public void testDataProcessor_Ok() {
        Storage.FruitTransactionStorage.clear();
        DataProcessor dataProcessor =
                new DataProcessorImpl(new ActivityStrategyImpl(activityHandlerMap));
        List<FruitTransaction> input = List.of(
                FruitTransaction.of(FruitTransaction.TypeOfActivity.BALANCE, "banana", 20),
                FruitTransaction.of(FruitTransaction.TypeOfActivity.BALANCE, "apple", 20),
                FruitTransaction.of(FruitTransaction.TypeOfActivity.SUPPLY, "banana", 30),
                FruitTransaction.of(FruitTransaction.TypeOfActivity.SUPPLY, "apple", 40),
                FruitTransaction.of(FruitTransaction.TypeOfActivity.PURCHASE, "banana", 10),
                FruitTransaction.of(FruitTransaction.TypeOfActivity.PURCHASE, "apple", 10));
        dataProcessor.process(input);
        Assert.assertEquals(40, Storage.FruitTransactionStorage.get("banana").intValue());
        Assert.assertEquals(50, Storage.FruitTransactionStorage.get("apple").intValue());
    }

    @Test
    public void testDataProcessor_NotOk() {
        DataProcessor dataProcessor =
                new DataProcessorImpl(new ActivityStrategyImpl(activityHandlerMap));
        List<FruitTransaction> input = List.of(
                FruitTransaction.of(FruitTransaction.TypeOfActivity.BALANCE, "banana", 20),
                FruitTransaction.of(FruitTransaction.TypeOfActivity.BALANCE, "apple", 20),
                FruitTransaction.of(FruitTransaction.TypeOfActivity.SUPPLY, "banana", 30),
                FruitTransaction.of(FruitTransaction.TypeOfActivity.SUPPLY, "apple", 40),
                FruitTransaction.of(FruitTransaction.TypeOfActivity.PURCHASE, "banana", 10000),
                FruitTransaction.of(FruitTransaction.TypeOfActivity.PURCHASE, "apple", 10));
        Assert.assertThrows(RuntimeException.class, () -> dataProcessor.process(input));
    }

    @Test
    public void testGenerator_Ok() {
        Storage.FruitTransactionStorage.clear();
        Storage.FruitTransactionStorage.put("banana", 30);
        Storage.FruitTransactionStorage.put("apple", 40);
        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,30" + System.lineSeparator() + "apple,40" + System.lineSeparator();
        Assert.assertEquals(expected, reportGenerator.generate());
    }

}
