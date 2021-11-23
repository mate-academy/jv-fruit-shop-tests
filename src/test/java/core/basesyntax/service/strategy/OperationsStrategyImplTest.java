package core.basesyntax.service.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.model.TransactionDto;
import core.basesyntax.service.operations.AdditionHendlerImpl;
import core.basesyntax.service.operations.OperationHendler;
import core.basesyntax.service.operations.SubstractionHendlerImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationsStrategyImplTest {
    private static final Map<TransactionDto.OperationTypes, OperationHendler> strategy =
            new HashMap<>();
    private final OperationsStrategy operationsStrategy = new OperationsStrategyImpl(strategy);

    @BeforeClass
    public static void beforeClass() throws Exception {
        strategy.put(TransactionDto.OperationTypes.BALANCE, new AdditionHendlerImpl());
        strategy.put(TransactionDto.OperationTypes.PURCHASE, new SubstractionHendlerImpl());
        strategy.put(TransactionDto.OperationTypes.RETURN, new AdditionHendlerImpl());
        strategy.put(TransactionDto.OperationTypes.SUPPLY, new AdditionHendlerImpl());
    }

    @Test
    public void get_validInput_ok() {
        OperationHendler expected = strategy.get(TransactionDto.OperationTypes.BALANCE);
        OperationHendler actual = operationsStrategy.get(TransactionDto.OperationTypes.BALANCE);
        assertEquals("Method should return: " + expected + " but was: " + actual,
                expected, actual);
        expected = strategy.get(TransactionDto.OperationTypes.PURCHASE);
        actual = operationsStrategy.get(TransactionDto.OperationTypes.PURCHASE);
        assertEquals("Method should return: " + expected + " but was: " + actual,
                expected, actual);
        expected = strategy.get(TransactionDto.OperationTypes.SUPPLY);
        actual = operationsStrategy.get(TransactionDto.OperationTypes.SUPPLY);
        assertEquals("Method should return: " + expected + " but was: " + actual,
                expected, actual);
        expected = strategy.get(TransactionDto.OperationTypes.RETURN);
        actual = operationsStrategy.get(TransactionDto.OperationTypes.RETURN);
        assertEquals("Method should return: " + expected + " but was: " + actual,
                expected, actual);
    }

    @Test
    public void get_invalidInput_ok() {
        OperationHendler expected = strategy.get(TransactionDto.OperationTypes.BALANCE);
        OperationHendler actual = operationsStrategy.get(TransactionDto.OperationTypes.PURCHASE);
        assertNotEquals("Method should return: " + expected + " but was: " + actual,
                expected, actual);
    }

    @Test
    public void get_nullInput_ok() {
        OperationHendler expected = null;
        OperationHendler actual = operationsStrategy.get(null);
        assertEquals("Method should return: " + expected + " but was: " + actual,
                expected, actual);
    }
}
