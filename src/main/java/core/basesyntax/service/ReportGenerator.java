package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;

import java.util.List;
import java.util.Map;

public interface ReportGenerator {
    List<String> getReport(Map<String, Integer> storage);
}
