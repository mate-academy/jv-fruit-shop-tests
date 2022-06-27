package core.basesyntax.service.impl;

import core.basesyntax.db.FruitDao;
import core.basesyntax.db.FruitDaoImpl;
import core.basesyntax.service.ReportCreator;
import java.util.Map;

public class ReportCreatorImpl implements ReportCreator {
    private static final String TITLE = "fruit,quantity";

    @Override
    public String create() {
        FruitDao dao = new FruitDaoImpl();
        if (dao.isEmpty()) {
            return "";
        }
        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append(TITLE);
        for (Map.Entry<String, Integer> entry : dao.getAll()) {
            String record = createRecord(entry);
            reportBuilder.append(System.lineSeparator()).append(record);
        }
        return reportBuilder.toString();
    }

    private String createRecord(Map.Entry<String, Integer> entry) {
        StringBuilder builder = new StringBuilder();
        String record = builder
                .append(entry.getKey())
                .append(",")
                .append(entry.getValue())
                .toString();
        return record;
    }
}
