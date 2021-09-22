package core.basesyntax.service.reporter;

import core.basesyntax.model.Fruit;
import java.util.Map;

public interface ReportGenerator {
    String createReport(Map<Fruit, Integer> storage);
}
