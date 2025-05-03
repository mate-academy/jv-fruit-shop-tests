package core.basesyntax.serviceimpl.transactionimpl;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.serviceintrface.transaction.ReportGenerateService;
import java.util.stream.Collectors;

public class ReportGenerateServiceImpl implements ReportGenerateService {
    private static final String HEADER_REPORT = "fruit,quantity";
    private static final String COMMA = ",";
    private final Storage fruitStorage;

    public ReportGenerateServiceImpl(Storage fruitStorage) {
        this.fruitStorage = fruitStorage;
    }

    @Override
    public String generateReport() {
        if (fruitStorage.getData().isEmpty()) {
            return HEADER_REPORT;
        }
        return HEADER_REPORT + System.lineSeparator()
                + fruitStorage.getData()
                .stream().map(entry -> entry.getKey() + COMMA + entry.getValue())
                .collect(Collectors.joining(System.lineSeparator()));
    }
}

