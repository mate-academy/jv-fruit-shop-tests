package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.service.ReportService;
import core.basesyntax.strategy.OperationCalculator;
import core.basesyntax.strategy.impl.BalanceOperationCalculator;
import core.basesyntax.strategy.impl.PurchaseCalculatorStrategyImpl;
import core.basesyntax.strategy.impl.ReturnCalculatorStrategyImpl;
import core.basesyntax.strategy.impl.SupplyCalculatorStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService ReportService;

    @BeforeClass
    public static void setUp() {
        Map<Operation, OperationCalculator> operationCalculatorsMap = new HashMap<>();
        operationCalculatorsMap.put(
                Operation.BALANCE, new BalanceOperationCalculator());
        operationCalculatorsMap.put(
                Operation.SUPPLY, new SupplyCalculatorStrategyImpl());
        operationCalculatorsMap.put(
                Operation.PURCHASE, new PurchaseCalculatorStrategyImpl());
        operationCalculatorsMap.put(
                Operation.RETURN, new ReturnCalculatorStrategyImpl());
        ReportService = new ReportServiceImpl();
    }

    @Test
    public void buildReport_validInputOne_ok() {
        FruitTransaction transactionOne = new FruitTransaction.FruitTransactionBuilder()
                .setOperation(Operation.BALANCE)
                .setFruit("Banana")
                .setQuantity(100).build();
        FruitTransaction transactionTwo = new FruitTransaction.FruitTransactionBuilder()
                .setOperation(Operation.SUPPLY)
                .setFruit("Apple")
                .setQuantity(50).build();
        FruitTransaction transactionThree = new FruitTransaction.FruitTransactionBuilder()
                .setOperation(Operation.PURCHASE)
                .setFruit("Apple")
                .setQuantity(20).build();
        List<FruitTransaction> inputList = List.of(
                transactionOne, transactionTwo, transactionThree);
        String expected = "fruit,quantity" + System.lineSeparator() + "Banana,130";
        String actual = ReportService.getReport();
        assertEquals(String.format(
                "Should return:%n%s%n when input is: %s%n but was:%n%s",
                expected, inputList, actual), expected, actual);
    }

    @Test
    public void buildReport_validInputTwo_ok() {
        FruitTransaction transactionOne = new FruitTransaction.FruitTransactionBuilder()
                .setOperation(Operation.BALANCE)
                .setFruit("Apple")
                .setQuantity(50).build();
        FruitTransaction transactionTwo = new FruitTransaction.FruitTransactionBuilder()
                .setOperation(Operation.RETURN)
                .setFruit("Apple")
                .setQuantity(6).build();
        FruitTransaction transactionThree = new FruitTransaction.FruitTransactionBuilder()
                .setOperation(Operation.BALANCE)
                .setFruit("Banana")
                .setQuantity(100).build();
        List<FruitTransaction> inputList = List.of(
                transactionOne, transactionTwo, transactionThree);
        String expected = "fruit,quantity" + System.lineSeparator() + "Apple,56"
                + System.lineSeparator() + "Banana,100";
        String actual = ReportService.getReport();
        assertEquals(String.format(
                "Should return:%n%s%n when input is: %s%n but was:%n%s",
                expected, inputList, actual), expected, actual);
    }

    @Test
    public void buildReport_emptyInput_ok() {
        String expected = "fruit,quantity";
        String actual = ReportService.getReport();
        assertEquals("Should return \"fruit,quantity\" if input is empty List, "
                + "but was: " + actual, expected, actual);
    }
}
