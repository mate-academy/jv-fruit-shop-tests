package core.basesyntax.service;

import java.io.File;
import java.util.Map;

public interface FileWriterService {
    String write(File fileName, Map<String, Integer> report);
}
