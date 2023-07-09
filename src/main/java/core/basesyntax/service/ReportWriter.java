package core.basesyntax.service;

import java.util.List;

public interface ReportWriter {

    void writeToFile(List<String> dailyTransactionsStringList, String reportFullPath);
}
