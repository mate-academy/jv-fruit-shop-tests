package core.basesyntax.servise.impl;

import core.basesyntax.dao.DaoStorage;
import core.basesyntax.exception.ReportMakerServiceException;
import core.basesyntax.servise.ReportMakerService;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ReportMakerServiceImpl implements ReportMakerService {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String SEPARATOR = ",";
    private static final String TITLE = "fruit,quantity" + LINE_SEPARATOR;
    private final DaoStorage daoStorage;

    public ReportMakerServiceImpl(DaoStorage daoStorage) {
        if (daoStorage == null) {
            throw new ReportMakerServiceException("The argument daoStorage is null");
        }
        this.daoStorage = daoStorage;
    }

    @Override
    public String generateReport() {
        Set<Map.Entry<String, Integer>> statistic = daoStorage.getStatistic();
        if (statistic.isEmpty()) {
            throw new ReportMakerServiceException("The Storage is empty");
        }
        return statistic.stream()
                .map(entry -> new StringBuilder()
                            .append(entry.getKey())
                            .append(SEPARATOR)
                            .append(entry.getValue()))
                .collect(Collectors.joining(LINE_SEPARATOR, TITLE, ""));
    }
}
