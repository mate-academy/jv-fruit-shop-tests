package mate.fruitshop;

import static mate.fruitshop.TransactionHandlerStrategyTest.DEFAULT_FRUIT;
import static mate.fruitshop.TransactionHandlerStrategyTest.DEFAULT_OPERATION;
import static mate.fruitshop.TransactionHandlerStrategyTest.DEFAULT_QUANTITY;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mate.fruitshop.dao.FruitTransactionDao;
import mate.fruitshop.model.FruitTransaction;
import mate.fruitshop.service.FruitService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class FruitServiceTest {

    public static final String HEADER = "fruit, quantity";
    @Mock
    private FruitTransactionDao dao;
    private final FruitService service;

    {
        MockitoAnnotations.openMocks(this);
        service = new FruitService(dao);
    }

    @Test
    void calculateFruitsLeft_ok() {
        List<FruitTransaction> transactionList = List.of(
                new FruitTransaction(DEFAULT_OPERATION, DEFAULT_FRUIT, DEFAULT_QUANTITY));
        when(dao.getAll()).thenReturn(transactionList);
        Assertions.assertEquals(Map.of(DEFAULT_FRUIT, DEFAULT_QUANTITY),
                service.calculateFruitsLeft());
    }

    @Test
    void calculateFruitsLeft_emptyList_Ok() {
        when(dao.getAll()).thenReturn(List.of());
        Assertions.assertEquals(Map.of(), service.calculateFruitsLeft());
    }

    @Test
    void calculateFruitsLeft_nullList_notOk() {
        when(dao.getAll()).thenReturn(null);
        Assertions.assertThrows(NullPointerException.class, service::calculateFruitsLeft);
    }

    @Test
    void calculateFruitsLeft_nullTransaction_notOk() {
        List<FruitTransaction> nullTransactionList = new ArrayList<>();
        nullTransactionList.add(null);
        when(dao.getAll()).thenReturn(nullTransactionList);
        Assertions.assertThrows(NullPointerException.class, service::calculateFruitsLeft);
    }

    @Test
    void createReport_ok() {
        Map<String, Integer> fruitQuantityMap = Map.of("apple", 20, "grapes", 7, "banana", 34);
        String report = service.createReport(fruitQuantityMap);
        Assertions.assertTrue(report.startsWith(HEADER + System.lineSeparator()));
        Assertions.assertTrue(report.contains(System.lineSeparator() + "apple, 20"));
        Assertions.assertTrue(report.contains(System.lineSeparator() + "grapes, 7"));
        Assertions.assertTrue(report.contains(System.lineSeparator() + "banana, 34"));
        Assertions.assertTrue(Character.isDigit(report.charAt(report.length() - 1)));
    }

    @Test
    void createReport_emptyReport_ok() {
        Assertions.assertEquals(HEADER, service.createReport(Map.of()));
    }
}
