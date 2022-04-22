package core.basesyntax.service;

import java.util.List;

public interface WriteFile {
    boolean writeFileReport(List<String> report, String filePath);
}
