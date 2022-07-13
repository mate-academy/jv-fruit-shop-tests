package core.basesyntax.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.service.ReportService;

public class ReportServiceImpl implements ReportService {
    private static final String FIRST_COLUMN_NAME = "fruit";
    private static final String SECOND_COLUMN_NAME = "quantity";
    private FruitDao dao;

    public ReportServiceImpl(FruitDao dao) {
        this.dao = dao;
    }

    @Override
    public String makeReport() {
        StringBuilder output = new StringBuilder();
        if (dao.getAll().size() > 0) {
            output.append(FIRST_COLUMN_NAME + ","
                            + SECOND_COLUMN_NAME + System.lineSeparator()
                            + dao.getAll().entrySet().stream()
                            .map(n -> n.getKey() + "," + n.getValue() + System.lineSeparator())
                            .sorted()
                            .reduce("", (a, b) -> a + b))
                    .delete(output.length() - System.lineSeparator().length(),
                            output.length());
        }
        return output.toString();
    }
}
