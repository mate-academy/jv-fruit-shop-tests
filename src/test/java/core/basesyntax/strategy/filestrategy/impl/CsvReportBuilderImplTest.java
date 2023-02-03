package core.basesyntax.strategy.filestrategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.strategy.filestrategy.ReportBuilder;
import core.basesyntax.strategy.operationstrategy.OperationCalculator;
import core.basesyntax.strategy.operationstrategy.OperationStrategy;
import core.basesyntax.strategy.operationstrategy.impl.BalanceOperationCalculatorImpl;
import core.basesyntax.strategy.operationstrategy.impl.PurchaseOperationCalculatorImpl;
import core.basesyntax.strategy.operationstrategy.impl.ReturnOperationCalculatorImpl;
import core.basesyntax.strategy.operationstrategy.impl.SupplyOperationCalculatorImpl;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvReportBuilderImplTest {
    private static Map<Operation, OperationCalculator> operationCalculatorsMap;
    private static OperationStrategy operationStrategy;
    private static ReportBuilder csvReportBuilder;

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
        OperationStrategy operationStrategy = new OperationStrategy(operationCalculatorsMap);
        csvReportBuilder = new CsvReportBuilderImpl(operationStrategy);
    }

    @Test
    public void buildReport_validInputOne_ok() {
        FruitTransaction transactionOne = new FruitTransaction.FruitTransactionBuilder()
                .setOperation(Operation.BALANCE)
                .setFruit("Apple")
                .setQuantity(10).build();
        FruitTransaction transactionTwo = new FruitTransaction.FruitTransactionBuilder()
                .setOperation(Operation.SUPPLY)
                .setFruit("Apple")
                .setQuantity(20).build();
        FruitTransaction transactionThree = new FruitTransaction.FruitTransactionBuilder()
                .setOperation(Operation.PURCHASE)
                .setFruit("Apple")
                .setQuantity(5).build();
        List<FruitTransaction> inputList = List.of(
                transactionOne, transactionTwo, transactionThree);
        String expected = "fruit,quantity" + System.lineSeparator() + "Apple,25";
        String actual = csvReportBuilder.buildReport(inputList);
        assertEquals(String.format(
                "Should return:%n%s%n when input is: %s%n but was:%n%s",
                expected, inputList, actual), expected, actual);
    }

    @Test
    public void buildReport_validInputTwo_ok() {
        FruitTransaction transactionOne = new FruitTransaction.FruitTransactionBuilder()
                .setOperation(Operation.BALANCE)
                .setFruit("Apple")
                .setQuantity(40).build();
        FruitTransaction transactionTwo = new FruitTransaction.FruitTransactionBuilder()
                .setOperation(Operation.RETURN)
                .setFruit("Apple")
                .setQuantity(8).build();
        FruitTransaction transactionThree = new FruitTransaction.FruitTransactionBuilder()
                .setOperation(Operation.BALANCE)
                .setFruit("Banana")
                .setQuantity(22).build();
        List<FruitTransaction> inputList = List.of(
                transactionOne, transactionTwo, transactionThree);
        String expected = "fruit,quantity" + System.lineSeparator() + "Apple,48"
                + System.lineSeparator() + "Banana,22";
        String actual = csvReportBuilder.buildReport(inputList);
        assertEquals(String.format(
                "Should return:%n%s%n when input is: %s%n but was:%n%s",
                expected, inputList, actual), expected, actual);
    }

    @Test
    public void buildReport_emptyInput_ok() {
        String expected = "fruit,quantity";
        String actual = csvReportBuilder.buildReport(Collections.emptyList());
        assertEquals("Should return \"fruit,quantity\" if input is empty List, "
                + "but was: " + actual, expected, actual);
    }

    @Test
    public void buildReport_nullInput_notOk() {
        String expected = "fruit,quantity";
        String actual = csvReportBuilder.buildReport(null);
        assertEquals("Should return \"fruit,quantity\" if input is null, "
                + "but was " + actual, expected, actual);
    }
}
