package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.ReportService;
import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportServiceImpl implements ReportService {
    private static final String SEPARATOR = ",";
    private static final String REPORT_HEADER = "fruit,quantity";

    @Override
    public List<String> create(List<FruitTransaction> fruitTransactions,
                               OperationStrategy operationStrategy) {
        List<String> report = new ArrayList<>();
        report.add(REPORT_HEADER);
        Map<String, IntSummaryStatistics> summaryMap = fruitTransactions.stream()
                .collect(Collectors.groupingBy(FruitTransaction::getFruit,
                        Collectors.summarizingInt(fruitTransaction -> operationStrategy
                                .get(fruitTransaction.getOperation())
                                .getValueByOperation(fruitTransaction.getQuantity()))));
        for (Map.Entry<String, IntSummaryStatistics> entry : summaryMap.entrySet()) {
            report.add(entry.getKey() + SEPARATOR + entry.getValue().getSum());
        }
        return report;
    }
}
