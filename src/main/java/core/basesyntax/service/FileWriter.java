package core.basesyntax.service;

import java.util.List;

public interface FileWriter {
    void write(List<String> record, String fileName);
}
