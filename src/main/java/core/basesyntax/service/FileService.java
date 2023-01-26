package core.basesyntax.service;

import java.util.List;

public interface FileService {
    List<String> readDataFromFile(String pathFile);

    void writeDataToFile(String pathFile, String data);
}
