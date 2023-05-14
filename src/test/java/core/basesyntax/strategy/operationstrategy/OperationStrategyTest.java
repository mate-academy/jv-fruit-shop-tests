package core.basesyntax.strategy.operationstrategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exception.UnsupportedOperationException;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.strategy.operationstrategy.impl.BalanceOperationCalculatorImpl;
import core.basesyntax.strategy.operationstrategy.impl.PurchaseOperationCalculatorImpl;
import core.basesyntax.strategy.operationstrategy.impl.ReturnOperationCalculatorImpl;
import core.basesyntax.strategy.operationstrategy.impl.SupplyOperationCalculatorImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
    private static Map<Operation, OperationCalculator> operationCalculatorsMap;
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void setUp() {
        Map<Operation, OperationCalculator> operationCalculatorsMap = new HashMap<>();
        operationCalculatorsMap.put(
                Operation.BALANCE, new BalanceOperationCalculatorImpl());
        operationCalculatorsMap.put(
                Operation.SUPPLY, new SupplyOperationCalculatorImpl());
        operationCalculatorsMap.put(
                Operation.PURCHASE, new PurchaseOperationCalculatorImpl());
        operationCalculatorsMap.put(
                Operation.RETURN, new ReturnOperationCalculatorImpl());
        operationStrategy = new OperationStrategy(operationCalculatorsMap);
    }

    @Test
    public void getOperationCalculator_validBalanceOperationInput_ok() {
        Class<BalanceOperationCalculatorImpl> expected = BalanceOperationCalculatorImpl.class;
        Class<? extends OperationCalculator> actual
                = operationStrategy.getOperationCalculator(Operation.BALANCE).getClass();
        assertEquals("Should return instance of BalanceOperationCalculatorImpl "
                + "when input is \"Operation.BALANCE\"", expected, actual);
    }

    @Test
    public void getOperationCalculator_validSupplyOperationInput_ok() {
        Class<SupplyOperationCalculatorImpl> expected = SupplyOperationCalculatorImpl.class;
        Class<? extends OperationCalculator> actual
                = operationStrategy.getOperationCalculator(Operation.SUPPLY).getClass();
        assertEquals("Should return instance of SupplyOperationCalculatorImpl "
                + "when input is \"Operation.SUPPLY\"", expected, actual);
    }

    @Test
    public void getOperationCalculator_validPurchaseOperationInput_ok() {
        Class<PurchaseOperationCalculatorImpl> expected = PurchaseOperationCalculatorImpl.class;
        Class<? extends OperationCalculator> actual
                = operationStrategy.getOperationCalculator(Operation.PURCHASE).getClass();
        assertEquals("Should return instance of PurchaseOperationCalculatorImpl "
                + "when input is \"Operation.PURCHASE\"", expected, actual);
    }

    @Test
    public void getOperationCalculator_validReturnOperationInput_ok() {
        Class<ReturnOperationCalculatorImpl> expected = ReturnOperationCalculatorImpl.class;
        Class<? extends OperationCalculator> actual
                = operationStrategy.getOperationCalculator(Operation.RETURN).getClass();
        assertEquals("Should return instance of ReturnOperationCalculatorImpl "
                + "when input is \"Operation.RETURN\"", expected, actual);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getOperationCalculator_nullInput_notOk() {
        operationStrategy.getOperationCalculator(null);
    }
}
