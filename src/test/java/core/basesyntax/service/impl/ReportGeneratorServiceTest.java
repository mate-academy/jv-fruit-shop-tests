package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ReportGeneratorService;
import core.basesyntax.strategy.ReportGeneratorServiceImpl;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReportGeneratorServiceTest {

    private ReportGeneratorService reportGenerator = new ReportGeneratorServiceImpl();

    @Test
    public void getReport_noTransactions_emptyReport() {
        List<FruitTransaction> transactions = new ArrayList<>();

        String expected = "fruit,quantity\n";
        String actual = reportGenerator.generateReport(transactions);

        assertEquals(expected, actual);
    }

    @Test
    public void getReport_withTransactions_correctReport() {
        // Транзакції є
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 50));

        String expected = "fruit,quantity\nbanana,100\napple,50\n";
        String actual = reportGenerator.generateReport(transactions);

        assertEquals(expected, actual);
    }
}
