package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.service.ReportService;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportServiceImpl implements ReportService {
    private final FruitDao dao;

    public ReportServiceImpl(FruitDao dao) {
        this.dao = dao;
    }

    @Override
    public String createReport() {
        return "fruit,quantity" + System.lineSeparator()
                + dao.getAll().entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByKey(
                        Comparator.comparingInt(fruit -> -fruit.getName().length()))))
                .map(map -> map.getKey().getName() + "," + map.getValue().toString())
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
