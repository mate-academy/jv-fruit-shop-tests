package core.basesyntax.servise.impl;

import core.basesyntax.dao.DaoStorage;
import core.basesyntax.servise.ReportMakerService;
import java.util.stream.Collectors;

public class ReportMakerServiceImpl implements ReportMakerService {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String SEPARATOR = ",";
    private static final String TITLE = "fruit,quantity" + LINE_SEPARATOR;
    private final DaoStorage daoStorage;

    public ReportMakerServiceImpl(DaoStorage daoStorage) {
        if (daoStorage == null) {
            throw new IllegalArgumentException("The argument daoStorage is null");
        }
        this.daoStorage = daoStorage;
    }

    @Override
    public String generateReport() {
        return daoStorage.getStatistic().stream()
                .map(entry -> new StringBuilder()
                            .append(entry.getKey())
                            .append(SEPARATOR)
                            .append(entry.getValue()))
                .collect(Collectors.joining(LINE_SEPARATOR, TITLE, ""));
    }
}
