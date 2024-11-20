package core.basesyntax.data;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.util.FileWriter;
import core.basesyntax.util.FileWriterImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopService {
    private final OperationStrategy operationStrategy;
    private final String path;

    public ShopService(OperationStrategy operationStrategy, String path) {
        this.operationStrategy = operationStrategy;
        this.path = path;
    }

    public void process(List<FruitTransaction> transactions) {
        Map<String, Integer> inventory = new HashMap<>();

        for (FruitTransaction transaction : transactions) {
            operationStrategy.execute(transaction.getOperation(), transaction, inventory);
        }
        ReportGenerator reportGenerator = new ReportGenerator();
        List<String> report = reportGenerator.generateReport(inventory);

        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.write(report, path);
    }
}
