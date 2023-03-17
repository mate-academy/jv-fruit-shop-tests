package core.basesyntax.services;

import java.util.List;

public interface DataParcerService {
    List<List<String>> parceDataFromCsv(List<String> nonParcedData);
}
