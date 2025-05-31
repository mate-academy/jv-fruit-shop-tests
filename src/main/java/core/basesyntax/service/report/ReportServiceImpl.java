package core.basesyntax.service.report;

import core.basesyntax.dao.FruitsDao;
import java.util.stream.Collectors;

public class ReportServiceImpl implements ReportService {
    private static final String HEADER = "fruit,quantity";
    private static final String DELIMITER = ",";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private FruitsDao fruitsDao;

    public ReportServiceImpl(FruitsDao fruitsDao) {
        this.fruitsDao = fruitsDao;
    }

    @Override
    public String reportAllFruits() {
        return HEADER + LINE_SEPARATOR + fruitsDao.getAllFruits()
                .entrySet().stream()
                .map(t -> t.getKey() + DELIMITER + t.getValue())
                .collect(Collectors.joining(LINE_SEPARATOR));
    }
}
