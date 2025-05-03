package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.service.ReportService;
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
                .map(map -> map.getKey().getName() + "," + map.getValue().toString())
                .sorted()
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
