package core.basesyntax.service;

import java.util.Collection;
import java.util.Map;

public interface ReportService<K, V> {
    Collection<String> generateReport(Collection<Map.Entry<K, V>> source);
}
