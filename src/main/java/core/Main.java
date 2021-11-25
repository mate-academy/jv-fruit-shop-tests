package core;

import core.dao.FruitDao;
import core.dao.FruitDaoImpl;
import core.model.Fruit;
import core.model.TransactionDto;
import core.service.Parser;
import core.service.impl.CreateReportImpl;
import core.service.impl.FileReaderImpl;
import core.service.impl.FileWriterImpl;
import core.service.impl.FruitServiceImpl;
import core.service.impl.ParserImpl;
import core.service.impl.ValidatorServiceImpl;
import core.strategy.Operation;
import core.strategy.OperationHandler;
import core.strategy.OperationStrategy;
import core.strategy.impl.AddOperationImpl;
import core.strategy.impl.BalanceOperationImpl;
import core.strategy.impl.OperationStrategyImpl;
import core.strategy.impl.PurchaseOperationImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String TEST_FILE_PATH = "src/main/resources/testFile.csv";
    private static final String REPORT_PATH = "src/main/resources/report.csv";

    public static void main(String[] args) {
        FruitDao fruitDao = new FruitDaoImpl();
        Map<Operation, OperationHandler> activityHandlerMap = new HashMap<>();
        activityHandlerMap.put(Operation.BALANCE, new BalanceOperationImpl(fruitDao));
        activityHandlerMap.put(Operation.PURCHASE, new PurchaseOperationImpl(fruitDao));
        activityHandlerMap.put(Operation.RETURN, new AddOperationImpl(fruitDao));
        activityHandlerMap.put(Operation.SUPPLY, new AddOperationImpl(fruitDao));
        List<String> text = new FileReaderImpl().readFromFile(TEST_FILE_PATH);
        new ValidatorServiceImpl().validate(text);
        OperationStrategy activitiesStrategy = new OperationStrategyImpl(activityHandlerMap);
        List<TransactionDto> transactions = new ArrayList<>();
        Parser parser = new ParserImpl();
        for (int i = 1; i < text.size(); i++) {
            transactions.add(parser.parseLine(text.get(i)));
        }
        List<Fruit> fruits = new FruitServiceImpl(fruitDao, activitiesStrategy)
                .changeBalance(transactions);
        String report = new CreateReportImpl().createReport(fruits);
        new FileWriterImpl().writeData(REPORT_PATH, report);
    }
}
