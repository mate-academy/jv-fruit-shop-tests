package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ReportGeneratorService;
import java.util.List;
import java.util.stream.Collectors;

public class ReportGeneratorServiceImpl implements ReportGeneratorService {
    @Override
    public String generate(List<FruitTransaction> transactions) {
        if (transactions.isEmpty()) {
            throw new RuntimeException("the list is empty");
        }
        return transactions.stream()
                .map(transaction -> String.join(",",
                        transaction.getFruit(),
                        String.valueOf(transaction.getQuantity())))
                .collect(Collectors.joining(System.lineSeparator()));
    }


}
