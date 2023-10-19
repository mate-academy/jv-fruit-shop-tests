package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;

import java.util.List;

public interface FruitService {
    void writeReport(String file, String toFile);
    String getReport(List<FruitTransaction> transactions);
}
