package core.basesyntax.dao;

import java.util.List;

public interface WriterService {

    String writeToFile(List<String> reportList, String filePath);
}
