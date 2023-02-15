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
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        transaction.setFruit("banana");
        transaction.setQuantity(100);
        FruitTransaction transaction1 = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction.setFruit("banana");
        transaction.setQuantity(10);
        FruitTransaction transaction2 = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction.setFruit("banana");
        transaction.setQuantity(7);
        FruitTransaction transaction3 = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        transaction.setFruit("apple");
        transaction.setQuantity(10);
        FruitTransaction transaction4 = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.RETURN);
        transaction.setFruit("apple");
        transaction.setQuantity(2);
        FruitTransaction transaction5 = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction.setFruit("apple");
        transaction.setQuantity(100);
        transactionsList.add(transaction);
        transactionsList.add(transaction1);
        transactionsList.add(transaction2);
        transactionsList.add(transaction3);
        transactionsList.add(transaction4);
        transactionsList.add(transaction5);
    }

    @Test
    public void generate_Ok() {
        reportGeneratorService.generate(transactionsList);
    }

    @Test(expected = RuntimeException.class)
    public void checkEmptyList_Ok() {
        List<FruitTransaction> testList = new ArrayList<>();
        reportGeneratorService.generate(testList);
    }
}
