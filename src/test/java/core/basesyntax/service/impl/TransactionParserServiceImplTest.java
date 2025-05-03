package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TransactionParserServiceImplTest {
    private TransactionParserService transactionParserService;

    @Before
    public void setUp() {
        transactionParserService = new TransactionParserServiceImpl();
    }

    @Test
    public void parseTransactions_Ok() {
        String data = "b,banana,20" + System.lineSeparator()
                + "b,apple,100" + System.lineSeparator()
                + "s,banana,100" + System.lineSeparator()
                + "p,banana,13" + System.lineSeparator()
                + "r,apple,10" + System.lineSeparator()
                + "p,apple,20" + System.lineSeparator()
                + "p,banana,5" + System.lineSeparator()
                + "s,banana,50";

        final List<FruitTransaction> parsedByService
                = transactionParserService.parseTransactions(data);
        final List<FruitTransaction> expected = new ArrayList<>();

        FruitTransaction transaction1 = new FruitTransaction();
        transaction1.setFruit(new Fruit("banana"));
        transaction1.setOperation(FruitTransaction.Operation.BALANCE);
        transaction1.setQuantity(20);
        expected.add(transaction1);

        FruitTransaction transaction2 = new FruitTransaction();
        transaction2.setFruit(new Fruit("apple"));
        transaction2.setOperation(FruitTransaction.Operation.BALANCE);
        transaction2.setQuantity(100);
        expected.add(transaction2);

        FruitTransaction transaction3 = new FruitTransaction();
        transaction3.setFruit(new Fruit("banana"));
        transaction3.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction3.setQuantity(100);
        expected.add(transaction3);

        FruitTransaction transaction4 = new FruitTransaction();
        transaction4.setFruit(new Fruit("banana"));
        transaction4.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction4.setQuantity(13);
        expected.add(transaction4);

        FruitTransaction transaction5 = new FruitTransaction();
        transaction5.setFruit(new Fruit("apple"));
        transaction5.setOperation(FruitTransaction.Operation.RETURN);
        transaction5.setQuantity(10);
        expected.add(transaction5);

        FruitTransaction transaction6 = new FruitTransaction();
        transaction6.setFruit(new Fruit("apple"));
        transaction6.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction6.setQuantity(20);
        expected.add(transaction6);

        FruitTransaction transaction7 = new FruitTransaction();
        transaction7.setFruit(new Fruit("banana"));
        transaction7.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction7.setQuantity(5);
        expected.add(transaction7);

        FruitTransaction transaction8 = new FruitTransaction();
        transaction8.setFruit(new Fruit("banana"));
        transaction8.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction8.setQuantity(50);
        expected.add(transaction8);

        Assert.assertEquals(expected, parsedByService);
    }
}
