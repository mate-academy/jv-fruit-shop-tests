package core.basesyntax.service;

import java.util.Map;

public interface ReportWriter {
    void write(Map<String, Integer> toWrite, String fileName);
}
