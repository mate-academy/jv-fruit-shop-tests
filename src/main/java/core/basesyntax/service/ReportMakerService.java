package core.basesyntax.service;

import java.util.Map;

public interface ReportMakerService {
    String generateReport(Map<String, Integer> calculations);
}
