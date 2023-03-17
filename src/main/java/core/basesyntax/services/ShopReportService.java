package core.basesyntax.services;

import java.util.List;
import java.util.Map;

public interface ShopReportService {
    List<String> generateReport(Map<String, Integer> processedData);
}
