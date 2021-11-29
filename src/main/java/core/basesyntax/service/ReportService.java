package core.basesyntax.service;

import java.util.Map;
import java.util.Set;

public interface ReportService {
    String formReport(Set<Map.Entry<String, Integer>> elements);
}
