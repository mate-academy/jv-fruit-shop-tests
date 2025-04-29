package core.basesyntax.service.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.infrastructure.db.Storage;
import core.basesyntax.service.FruitTransaction;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.ReportGeneratorImpl;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {

    @Test
    public void pushareOperationOk() {
        Storage.STORAGE.remove("apple");
        OperationHandler balanceOperation = new BalanceOperation();
        balanceOperation.run(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        OperationHandler pushareOperation = new PurchaseOperation();
        pushareOperation.run(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 10));
        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        String actual = reportGenerator.getReport();

        String expected = "banana,10";

        assertEquals(actual, expected);
    }

    @Test
    public void notEnoughProductNotOk() {
        OperationHandler balanceOperation = new BalanceOperation();
        balanceOperation.run(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        OperationHandler pushareOperation = new PurchaseOperation();
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> pushareOperation.run(new FruitTransaction(
                        FruitTransaction.Operation.PURCHASE, "banana", 40)));

        assertEquals("Too little of product: banana", exception.getMessage());
    }
}
