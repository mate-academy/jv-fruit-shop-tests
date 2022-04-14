package core.basesyntax;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.operation.AddOperation;
import core.basesyntax.service.operation.Calculator;
import core.basesyntax.service.operation.SubtractionOperation;
import core.basesyntax.service.read.FileReader;
import core.basesyntax.service.read.FileReaderImpl;
import core.basesyntax.service.storage.StoreService;
import core.basesyntax.service.storage.StoreServiceImpl;
import core.basesyntax.service.validator.DataValidator;
import core.basesyntax.service.validator.DataValidatorImpl;
import core.basesyntax.service.write.FileWriter;
import core.basesyntax.service.write.FileWriterImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String INPUT_FILE_NAME = "src/main/resources/input.csv";
    private static final String OUTPUT_FILE_NAME = "src/main/resources/report.csv";

    public static void main(String[] args) {
        FileReader fileReader = new FileReaderImpl();
        List<String> fruitRecords = fileReader.read(INPUT_FILE_NAME);
        DataValidator<String> dataValidator = new DataValidatorImpl();
        for (String temp : fruitRecords) {
            try {
                dataValidator.validate(temp);
            } catch (ValidationException e) {
                throw new RuntimeException("Data is not valid" + temp, e);
            }
        }

        Map<Fruit.OperationType, Calculator> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Fruit.OperationType.BALANCE, new AddOperation());
        operationHandlerMap.put(Fruit.OperationType.PURCHASE, new SubtractionOperation());
        operationHandlerMap.put(Fruit.OperationType.SUPPLY, new AddOperation());
        operationHandlerMap.put(Fruit.OperationType.RETURN, new AddOperation());

        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        FruitsDao fruitsDao = new FruitsDaoImpl();
        StoreService storeService = new StoreServiceImpl(operationStrategy, fruitsDao);
        storeService.applyToDb(fruitRecords);
        String report = storeService.getDbReport();
        FileWriter writeService = new FileWriterImpl();
        writeService.write(report, OUTPUT_FILE_NAME);
    }
}
