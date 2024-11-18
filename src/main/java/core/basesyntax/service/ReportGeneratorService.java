package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;

import java.util.List;

public interface ReportGeneratorService {
    String generateReport(List<FruitTransaction> transactions);

}
