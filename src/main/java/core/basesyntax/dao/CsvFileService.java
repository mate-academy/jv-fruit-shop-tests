package core.basesyntax.dao;

import java.util.List;

public interface CsvFileService {
    List<String> readFromFile(String filePath);

    void writeToFile(String stringForReport, String outFilePath);
}
