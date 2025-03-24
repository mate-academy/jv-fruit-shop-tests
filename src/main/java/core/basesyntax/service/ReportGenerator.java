package core.basesyntax.service;

import java.util.List;
import java.util.Map;

import core.basesyntax.model.FruitTransaction;

public interface ReportGenerator {
    List<String> getReport(Map<String, Integer> storage);
}
