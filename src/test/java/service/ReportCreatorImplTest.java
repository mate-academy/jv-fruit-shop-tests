package service;

import java.util.ArrayList;
import java.util.List;
import model.FruitTransaction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorImplTest {
    private static ReportCreator reportCreator;
    private static StringBuilder result;
    private static List<FruitTransaction> transactionList;
    private static FruitTransaction fruitTransaction;
    private static FruitTransaction secondFruitTransaction;

    @BeforeClass
    public static void beforeClass() throws Exception {
        reportCreator = new ReportCreatorImpl();
        result = new StringBuilder();
        transactionList = new ArrayList<>();
        fruitTransaction = new FruitTransaction();
        secondFruitTransaction = new FruitTransaction();
    }

    @Test
    public void correctReport_OK() {
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(172);
        secondFruitTransaction.setFruit("apple");
        secondFruitTransaction.setQuantity(190);
        transactionList.add(fruitTransaction);
        transactionList.add(secondFruitTransaction);
        result.append("fruit,quantity");
        result.append(System.lineSeparator());
        result.append("banana,172");
        result.append(System.lineSeparator());
        result.append("apple,190");
        result.append(System.lineSeparator());
        String expected = result.toString();
        String actual = reportCreator.createReport(transactionList);
        Assert.assertEquals(expected, actual);
    }
}
