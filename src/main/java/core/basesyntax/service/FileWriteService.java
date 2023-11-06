package core.basesyntax.service;

import java.util.List;

public interface FileWriteService {
    void writeCsvToFile(List<String> text, String path);
}
