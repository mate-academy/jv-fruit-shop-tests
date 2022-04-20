package service;

import java.util.List;
import java.util.stream.Collectors;
import model.FruitTransaction;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ReaderImpl {
    @Test
    public void createReport_Ok() {
        List<FruitTransaction> transactions = List.of(
                getFruitTransaction("banana", 172),
                getFruitTransaction("banana", 172),
                getFruitTransaction("apple", 190)
        );

        List<FruitTransaction> cleanListWithoutDuplicates = transactions.stream()
                .distinct().collect(Collectors.toList());

        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "banana,172"
                + System.lineSeparator()
                + "apple,190"
                + System.lineSeparator();
        ReportCreator reportCreator = new ReportCreatorImpl();
        String actual = reportCreator.createReport(cleanListWithoutDuplicates);

        Assert.assertEquals(expected, actual);
    }

    private FruitTransaction getFruitTransaction(String fruit, int quantity) {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit(fruit);
        fruitTransaction.setQuantity(quantity);
        return fruitTransaction;
    }
}
