package core.basesyntax.service.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.FruitTransaction;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.ReportGeneratorImpl;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    @Test
    void returnOperationOk() {
        OperationHandler balanceOperation = new BalanceOperation();
        balanceOperation.run(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 20));
        OperationHandler returnOperation = new ReturnOperation();
        returnOperation.run(new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 20));
        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        String actual = reportGenerator.getReport();

        String expected = "banana,40";

        assertEquals(actual, expected);
    }
}
