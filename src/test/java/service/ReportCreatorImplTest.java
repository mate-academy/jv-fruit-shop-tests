package service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import model.FruitTransaction;
import org.junit.Assert;
import org.junit.Test;

public class ReportCreatorImplTest {
    private final FruitTransaction secondFruitTransaction = new FruitTransaction();
    private final FruitTransaction extraPositionInReport = new FruitTransaction();
    private final FruitTransaction fruitTransaction = new FruitTransaction();

    @Test

    public void createReport_Ok() {
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(172);
        extraPositionInReport.setFruit("banana");//дубликат банана
        extraPositionInReport.setQuantity(172);//дубликат кол-во банана
        secondFruitTransaction.setFruit("apple");
        secondFruitTransaction.setQuantity(190);
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(fruitTransaction);
        transactions.add(secondFruitTransaction);
        transactions.add(extraPositionInReport);//добавили дубликат в лист

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
}
