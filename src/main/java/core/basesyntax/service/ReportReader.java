package core.basesyntax.service;

import java.util.List;

public interface ReportReader {
    List<String> getListOfTransactions(String transactionFullPath);
}
