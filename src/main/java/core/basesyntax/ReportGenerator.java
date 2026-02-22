package core.basesyntax;

import java.util.List;
import java.util.Map;

public interface ReportGenerator {
    public List<String> getReport(Map<String, Integer> storage);
}
