package service;

import java.util.ArrayList;
import java.util.List;
import model.FruitTransaction;
import org.junit.Assert;
import org.junit.Test;

public class ReportCreatorImplTest {
    @Test
    public void createReport_Ok() {
        FruitTransaction secondFruitTransaction;
        FruitTransaction fruitTransaction = new FruitTransaction();
        secondFruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(172);
        secondFruitTransaction.setFruit("apple");
        secondFruitTransaction.setQuantity(190);
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(fruitTransaction);
        transactions.add(secondFruitTransaction);
        StringBuilder builder = new StringBuilder();
        builder.append("fruit,quantity");
        builder.append(System.lineSeparator());
        builder.append("banana,172");
        builder.append(System.lineSeparator());
        builder.append("apple,190");
        builder.append(System.lineSeparator());
        String expected = builder.toString();
        ReportCreator reportCreator = new ReportCreatorImpl();
        String actual = reportCreator.createReport(transactions);
        Assert.assertEquals(expected, actual);
    }
}
