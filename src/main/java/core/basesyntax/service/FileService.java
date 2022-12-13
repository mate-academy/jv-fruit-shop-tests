package core.basesyntax.service;

import java.util.List;

public interface FileService {

    List<String> readFromFile(String fileName);

    boolean writeToFile(String data, String fileName);
}
