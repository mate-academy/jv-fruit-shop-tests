package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ReportGeneratorService;
import java.util.List;

public class ReportGeneratorServiceImpl implements ReportGeneratorService {
    @Override
    public String generateReport(List<FruitTransaction> transactions) {
        if (transactions == null || transactions.isEmpty()) {
            return "fruit,quantity\n";
        }

        StringBuilder report = new StringBuilder("fruit,quantity\n");

        for (FruitTransaction transaction : transactions) {
            report.append(transaction.getFruit())
                    .append(",")
                    .append(transaction.getQuantity())
                    .append("\n");
        }

        return report.toString();
    }
}
