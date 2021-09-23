package core.basesyntax.service;

import java.util.List;

public interface FileService {
    List<String> readFromFile(String fromInputFile);

    boolean writeToReportFile(String informationString, String fromFilePath);
}
