package core.basesyntax.service;

import java.util.Map;

public interface Writer {
    void write(String toFileName, Map<String, Integer> mapToWrite);
}
