package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.strategy.FruitHandler;
import core.basesyntax.strategy.FruitHandlerStrategy;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class FruitServiceTest {
    private FruitService fruitService;
    private FruitHandlerStrategy fruitHandlerStrategy;
    private FruitTransaction transaction;
    private FruitHandler fruitHandler;
    private List<FruitTransaction> transactions;

    @BeforeEach
    void setUp() {
        fruitService = new FruitServiceImpl();
        fruitHandlerStrategy = Mockito.mock(FruitHandlerStrategy.class);
        transaction = Mockito.mock(FruitTransaction.class);
        fruitHandler = Mockito.mock(FruitHandler.class);
        transactions = new ArrayList<>();
        transactions.add(transaction);
    }

    @Test
    void fruitService_validData_ok() {
        Mockito.when(fruitHandlerStrategy.get(transaction))
                .thenReturn(fruitHandler);
        fruitService.processAllTransations(transactions, fruitHandlerStrategy);
        Mockito.verify(fruitHandler)
                .doAction(transaction);
    }

    @Test
    void fruitService_withNullHandler_notOk() {
        Mockito.when(fruitHandlerStrategy.get(transaction))
                .thenReturn(null);
        fruitService.processAllTransations(transactions, fruitHandlerStrategy);
        Mockito.verifyNoInteractions(transaction);
    }
}
