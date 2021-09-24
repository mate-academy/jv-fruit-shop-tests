package core.basesyntax.service;

import java.util.Map;

public interface WriteFile {
    boolean writeWithMapToFile(Map<String, Integer> map, String reportPath);
}
