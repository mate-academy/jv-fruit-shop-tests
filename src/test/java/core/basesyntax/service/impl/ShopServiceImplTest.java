package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import core.basesyntax.db.FruitDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {
    private OperationStrategy operationStrategy;
    private FruitDao fruitDao;
    private ShopServiceImpl shopService;
    private final FruitTransaction firstTransaction = new FruitTransaction();
    private final FruitTransaction secondTransaction = new FruitTransaction();

    @BeforeEach
    public void setUp() {
        operationStrategy = mock(OperationStrategy.class);
        fruitDao = mock(FruitDao.class);
        shopService = new ShopServiceImpl(operationStrategy, fruitDao);

        firstTransaction.setFruit("apple");
        firstTransaction.setQuantity(10);
        firstTransaction.setOperation("b");

        secondTransaction.setFruit("banana");
        secondTransaction.setQuantity(5);
        secondTransaction.setOperation("b");
    }

    @Test
    public void get_FruitMap_Ok() {
        Map<String, Integer> expectedMap = new HashMap<>();
        expectedMap.put("apple", 10);
        when(fruitDao.getAllFruits()).thenReturn(expectedMap);

        Map<String, Integer> actualMap = shopService.getFruitMap();
        assertEquals(expectedMap, actualMap);
    }

    @Test
    public void proceedAll_WithValidTransactions_Ok() {
        List<FruitTransaction> transactions = Arrays.asList(firstTransaction, secondTransaction);

        OperationHandler handler = mock(OperationHandler.class);
        when(operationStrategy.choseOperationHandler(any())).thenReturn(handler);

        shopService.proceedAll(transactions);

        verify(operationStrategy, times(2)).choseOperationHandler(any());
        verify(handler, times(2)).executeOperation(eq(fruitDao), any(FruitTransaction.class));
    }

    @Test
    public void proceedAll_WithNullTransaction_NotOk() {
        List<FruitTransaction> transactions = Arrays.asList(
                firstTransaction,
                null
        );

        OperationHandler handler = mock(OperationHandler.class);
        when(operationStrategy.choseOperationHandler(any())).thenReturn(handler);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> shopService.proceedAll(transactions));
        assertEquals("Transaction is null: [FruitTransaction{fruit='apple'"
                        + ", quantity=10, operation=BALANCE}, null]",
                exception.getMessage());
    }
}
