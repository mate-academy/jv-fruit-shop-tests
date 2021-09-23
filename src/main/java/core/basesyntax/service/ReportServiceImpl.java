package core.basesyntax.service;

import core.basesyntax.bd.Storage;
import java.util.Map;

public class ReportServiceImpl implements ReportService {
    private OperationStrategy operationStrategy;

    public ReportServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public boolean getReport(String pathFrom, String pathTo) {
        boolean checkReader = new FileReaderImpl().read(pathFrom);
        if (!checkReader) {
            return false;
        }
        new FruitShopServiceImpl().transfer(operationStrategy);
        String dataForWrite = createReport();
        boolean checkWriter = new FileWriterImpl().write(dataForWrite, pathTo);
        if (!checkWriter) {
            return false;
        }
        return true;
    }

    private String createReport() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Integer> tempEntry : Storage.fruitQuantity.entrySet()) {
            stringBuilder.append(tempEntry.getKey())
                    .append(",").append(tempEntry.getValue())
                    .append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }
}
