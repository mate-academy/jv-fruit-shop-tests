package core.basesyntax.service;

import java.util.List;

public interface ReaderServiceImpl {
    List<String> readFromFileInput(String filepath);

    List<String> readFromFileReport(String filepath);

}

