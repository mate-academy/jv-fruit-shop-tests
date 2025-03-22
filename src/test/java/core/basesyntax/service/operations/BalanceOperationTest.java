package core.basesyntax.service.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.infrastructure.db.Storage;
import core.basesyntax.service.FruitTransaction;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.ReportGeneratorImpl;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    @Test
    void balanceOperationOk() {
        Storage.STORAGE.remove("apple");
        OperationHandler balanceOperation = new BalanceOperation();
        balanceOperation.run(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        String actual = reportGenerator.getReport();

        String expected = "banana,20";

        assertEquals(actual, expected);
    }
}
