package core.basesyntax.service;

import java.util.Map;

public interface ReportGenerator {
    String create(Map<String, Integer> reportData);
}
