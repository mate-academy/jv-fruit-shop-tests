package basesyntax;

import basesyntax.model.FruitRecordDto;
import basesyntax.service.AmountCalculator;
import basesyntax.service.AmountCalculatorImpl;
import basesyntax.service.OperationStrategy;
import basesyntax.service.OperationStrategyImpl;
import basesyntax.service.ReportService;
import basesyntax.service.ReportServiceImpl;
import basesyntax.service.data.DataParser;
import basesyntax.service.data.DataParserImpl;
import basesyntax.service.file.ReaderService;
import basesyntax.service.file.ReaderServiceImpl;
import basesyntax.service.file.WriterService;
import basesyntax.service.file.WriterServiceImpl;
import basesyntax.service.operation.DecreaseOperationHandler;
import basesyntax.service.operation.IncreaseOperationHandler;
import basesyntax.service.operation.OperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String FROM_FILE_CSV = "src/main/resources/input.csv";
    private static final String TO_FILE_CSV = "src/main/resources/output.csv";

    public static void main(String[] args) {
        Map<FruitRecordDto.Type, OperationHandler> operationStrategyMap = new HashMap<>();
        operationStrategyMap.put(FruitRecordDto.Type.BALANCE, new IncreaseOperationHandler());
        operationStrategyMap.put(FruitRecordDto.Type.SUPPLY, new IncreaseOperationHandler());
        operationStrategyMap.put(FruitRecordDto.Type.PURCHASE, new DecreaseOperationHandler());
        operationStrategyMap.put(FruitRecordDto.Type.RETURN, new IncreaseOperationHandler());
        ReaderService readerService = new ReaderServiceImpl();
        List<String> dataFromFile = readerService.readFromFile(FROM_FILE_CSV);
        DataParser<FruitRecordDto, String> dataParser = new DataParserImpl();
        List<FruitRecordDto> fruitRecordDtos = dataParser.formatData(dataFromFile);
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationStrategyMap);
        AmountCalculator amountCalculator = new AmountCalculatorImpl(operationStrategy);
        Map<String, Integer> calculateFruits
                = amountCalculator.calculateDataForReport(fruitRecordDtos);
        ReportService reportService = new ReportServiceImpl();
        WriterService writerService = new WriterServiceImpl();
        writerService.write(TO_FILE_CSV, reportService.createReport(calculateFruits));
    }
}
