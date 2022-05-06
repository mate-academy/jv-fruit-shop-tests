package service;

import static org.junit.Assert.assertEquals;

import dao.StorageDaoImpl;
import db.Storage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.Fruit;
import model.FruitTransaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationServiceImplTest {
    private static OperationService operationService;
    private static OperationHandlerStrategy operationHandlerStrategy;
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl(new StorageDaoImpl());
        operationHandlerStrategy = new OperationHandlerStrategyImpl();
        operationService = new OperationServiceImpl(operationHandlerStrategy);
    }

    @Test
    public void calculate_emptyList_Ok() {
        List<FruitTransaction> info = new ArrayList<>();
        operationService.calculate(info);
    }

    @Test
    public void calculate_correctTransactionList_Ok() {
        Fruit banana = new Fruit("banana");
        Fruit apple = new Fruit("apple");
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,banana,20));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,banana,20));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,banana,20));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,apple,50));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.RETURN,apple,10));
        operationService.calculate(fruitTransactions);
        StringBuilder actual = new StringBuilder();
        for (Map.Entry<Fruit, Integer> entry : Storage.dataBase.entrySet()) {
            actual.append(entry.getKey().getFruit() + ","
                    + entry.getValue() + System.lineSeparator());
        }
        String expected = "banana,20"
                + System.lineSeparator()
                + "apple,60"
                + System.lineSeparator();
        assertEquals(expected, actual.toString());
    }

    @Test(expected = RuntimeException.class)
    public void calculate_wrongTransaction_notOk() {
        Fruit banana = new Fruit("banana");
        List<FruitTransaction> list = List.of(new FruitTransaction(null, banana, 100));
        operationService.calculate(list);
    }

    @After
    public void tearDown() {
        Storage.dataBase.clear();
    }
}
