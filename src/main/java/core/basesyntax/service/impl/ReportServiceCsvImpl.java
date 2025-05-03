package core.basesyntax.service.impl;

import static java.util.stream.Collectors.joining;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.service.ReportService;
import java.util.Map;

public class ReportServiceCsvImpl implements ReportService {
    private static final String HEADERS = "fruit,quantity";
    private static final String DELIMITER = ",";
    private final FruitDao fruitDao = new FruitDaoImpl();

    @Override
    public String createReport() {
        Map<String, Integer> fruitStorage = fruitDao.getAll();
        return HEADERS + (fruitStorage.size() != 0 ? System.lineSeparator() : "")
                + fruitStorage.entrySet()
                .stream()
                .map(entry -> entry.getKey() + DELIMITER + entry.getValue())
                .collect(joining(System.lineSeparator()));
    }
}
