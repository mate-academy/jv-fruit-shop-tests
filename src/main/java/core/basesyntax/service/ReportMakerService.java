package core.basesyntax.service;

import java.util.Map;

public interface ReportMakerService {
    void prepareReport(Map<String, Integer> fruitsMap, String toFile);
}
