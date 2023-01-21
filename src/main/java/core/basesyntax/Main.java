package core.basesyntax;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import core.basesyntax.strategy.SummaryData;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String fileInput = "src/main/resources/allTransactionForPeriod.csv";
        List<Transaction> listTransactions = new ReaderServiceImpl().getListTransaction(fileInput);
        List<Transaction> listTotalResults = new SummaryData().getTotalResult(listTransactions);
        new WriterServiceImpl().writeFile(listTotalResults);
    }
}
