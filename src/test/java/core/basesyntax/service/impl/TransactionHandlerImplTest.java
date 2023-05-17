package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceTransactionHandlerImpl;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

class TransactionHandlerImplTest {
    @Test
    void parseWithEmptyInputList_Ok() {
        TransactionHandler handler
                = new TransactionHandlerImpl(new OperationStrategyImpl(Collections.emptyMap()));
        List<FruitTransaction> inputList = Collections.emptyList();
        assertDoesNotThrow(() -> handler.parse(inputList));
    }

    @Test
    void parseThrowException_Ok() {
        OperationStrategy strategy = new OperationStrategyImpl(Collections.emptyMap());
        TransactionHandler handler = new TransactionHandlerImpl(strategy);
        List<FruitTransaction> inputList = List.of(new FruitTransaction());
        assertThrows(NullPointerException.class, () -> handler.parse(inputList));
    }

    @Test
    void parseCallTransactionOfOperationHandler_Ok() {
        OperationStrategy strategy = new OperationStrategyImpl(Collections
                .singletonMap(FruitTransaction.Operation.BALANCE,
                        new BalanceTransactionHandlerImpl()));
        TransactionHandler transactionHandler = new TransactionHandlerImpl(strategy);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        transaction.setFruit(new Fruit("some fruit", 1));
        List<FruitTransaction> inputList = List.of(transaction);
        assertDoesNotThrow(() -> transactionHandler.parse(inputList));
    }
}
