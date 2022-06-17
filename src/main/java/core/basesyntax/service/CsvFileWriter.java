package core.basesyntax.service;

import java.util.List;

public interface CsvFileWriter {
    boolean write(List<String> generatedFruitReport, String pathname);
}
