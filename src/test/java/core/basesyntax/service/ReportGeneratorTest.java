package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.operations.BalanceOperation;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReportGeneratorTest {
    private static ReportGenerator reportGenerator;
    private static BalanceOperation balanceOperation;

    @BeforeAll
    public static void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        balanceOperation = new BalanceOperation();
    }

    @Test
    void generateReport_Ok() {
        FruitTransaction appleBalance =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 1);
        FruitTransaction bananaBalance =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10);
        FruitTransaction cherryBalance =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "cherry", 100);
        balanceOperation.processTransaction(appleBalance);
        balanceOperation.processTransaction(bananaBalance);
        balanceOperation.processTransaction(cherryBalance);
        assertEquals(1, Storage.shop.get("apple"));
        assertEquals(10, Storage.shop.get("banana"));
        assertEquals(100, Storage.shop.get("cherry"));
        String actual = reportGenerator.generateReport();
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,10" + System.lineSeparator() + "apple,1" + System.lineSeparator()
                + "cherry,100" + System.lineSeparator();
        assertEquals(expected, actual);
    }
}
