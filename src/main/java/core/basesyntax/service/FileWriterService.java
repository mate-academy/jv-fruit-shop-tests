package core.basesyntax.service;

import java.util.List;

public interface FileWriterService {
    boolean write(List<String> statistic, String filePath);
}
