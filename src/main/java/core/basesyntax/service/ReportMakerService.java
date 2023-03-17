package core.basesyntax.service;

import java.nio.file.Path;
import java.util.Map;

public interface ReportMakerService {
    Path createReport(Map<String, Integer> storage, String path);
}
