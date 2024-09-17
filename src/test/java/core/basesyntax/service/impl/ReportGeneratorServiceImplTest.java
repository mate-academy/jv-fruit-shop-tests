package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ReportGeneratorService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ReportGeneratorServiceImplTest {
    private final ReportGeneratorService reportGeneratorService
            = new ReportGeneratorServiceImpl();
    private List<FruitTransaction> transactionsList;

    @Before
    public void setUp() {
        transactionsList = new ArrayList<>();
        final FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        transaction.setFruit("banana");
        transaction.setQuantity(100);
        transactionsList.add(transaction);
        final FruitTransaction transaction1 = new FruitTransaction();
        transaction1.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction1.setFruit("banana");
        transaction1.setQuantity(10);
        transactionsList.add(transaction1);
        final FruitTransaction transaction2 = new FruitTransaction();
        transaction2.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction2.setFruit("banana");
        transaction2.setQuantity(7);
        transactionsList.add(transaction2);
        final FruitTransaction transaction3 = new FruitTransaction();
        transaction3.setOperation(FruitTransaction.Operation.BALANCE);
        transaction3.setFruit("apple");
        transaction3.setQuantity(10);
        transactionsList.add(transaction3);
        final FruitTransaction transaction4 = new FruitTransaction();
        transaction4.setOperation(FruitTransaction.Operation.RETURN);
        transaction4.setFruit("apple");
        transaction4.setQuantity(2);
        transactionsList.add(transaction4);
        final FruitTransaction transaction5 = new FruitTransaction();
        transaction5.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction5.setFruit("apple");
        transaction5.setQuantity(100);
        transactionsList.add(transaction5);
    }

    @Test
    public void generate_Ok() {
        reportGeneratorService.generate(transactionsList);
    }

    @Test(expected = RuntimeException.class)
    public void generate_checkEmptyList_Ok() {
        List<FruitTransaction> testList = new ArrayList<>();
        reportGeneratorService.generate(testList);
    }
}
