package core.basesyntax.basesyntax.service;

import core.basesyntax.basesyntax.model.Fruit;
import java.util.Map;

public interface ReportService {
    String createReport(Map<Fruit, Integer> fruits);
}

