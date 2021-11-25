package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import java.util.Map;

public class ReportServiceImpl implements ReportService {
    private final FruitDao dao;

    public ReportServiceImpl(FruitDao dao) {
        this.dao = dao;
    }

    @Override
    public String createReport() {
        StringBuilder builder = new StringBuilder();
        builder.append("fruit,quantity");
        for (Map.Entry<Fruit, Integer> set : dao.getAll().entrySet()) {
            builder.append(System.lineSeparator()).append(set.getKey().getName())
                    .append(",").append(set.getValue());
        }
        return builder.toString();
    }
}
