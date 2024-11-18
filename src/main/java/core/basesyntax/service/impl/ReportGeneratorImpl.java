package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ReportGeneratorService;
import java.util.List;

public class ReportGeneratorImpl implements ReportGeneratorService {
    @Override
    public String generateReport(List<FruitTransaction> transactions) {
        StringBuilder report = new StringBuilder("fruit,quantity\n");

        if (transactions != null && !transactions.isEmpty()) {
            for (FruitTransaction transaction : transactions) {
                report.append(transaction.getFruit())
                        .append(",")
                        .append(transaction.getQuantity())
                        .append("\n");
            }
        }
        return report.toString();
    }
}

