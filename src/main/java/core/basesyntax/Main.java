package core.basesyntax;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.OperationType;
import core.basesyntax.service.FileReaderServiceImpl;
import core.basesyntax.service.FileWriterServiceImpl;
import core.basesyntax.service.FruitBalanceServiceImpl;
import core.basesyntax.service.FruitPurchaseServiceImpl;
import core.basesyntax.service.FruitReturnServiceImpl;
import core.basesyntax.service.FruitSupplyServiceImpl;
import core.basesyntax.service.ReportCreateService;
import core.basesyntax.service.TransactionDto;
import core.basesyntax.service.TransactionDtoParser;
import core.basesyntax.service.interfaces.FileReaderService;
import core.basesyntax.service.interfaces.FileWriterService;
import core.basesyntax.service.interfaces.FruitOperationService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String INPUT_PATH = "src/main/resources/file.csv";
    private static final String OUTPUT_PATH = "src/main/resources/reportFile.csv";

    public static void main(String[] args) {
        FruitDao fruitDao = new FruitDaoImpl();
        Map<OperationType, FruitOperationService> strategyOperation = new HashMap<>();
        strategyOperation.put(OperationType.BALANCE,
                new FruitBalanceServiceImpl(fruitDao));
        strategyOperation.put(OperationType.SUPPLY,
                new FruitSupplyServiceImpl(fruitDao));
        strategyOperation.put(OperationType.PURCHASE,
                new FruitPurchaseServiceImpl(fruitDao));
        strategyOperation.put(OperationType.RETURN,
                new FruitReturnServiceImpl(fruitDao));

        FileReaderService fileReaderService = new FileReaderServiceImpl();
        List<String> fileContent = fileReaderService.readFromFile(INPUT_PATH);
        List<TransactionDto> transactionList = new TransactionDtoParser()
                .parse(fileContent);
        for (TransactionDto transaction : transactionList) {
            FruitOperationService fruitOperationService = strategyOperation
                    .get(transaction.getType());
            fruitOperationService.apply(transaction);
        }

        String finalReport = new ReportCreateService(fruitDao).createReport();
        FileWriterService fileWriterService = new FileWriterServiceImpl();
        fileWriterService.writeToFile(finalReport, OUTPUT_PATH);
    }
}
