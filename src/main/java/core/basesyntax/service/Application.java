package core.basesyntax.service;

import core.basesyntax.model.dto.FruitRecordDto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Application {
    private static final String INPUT_FILE_PATH = "src/main/java/resources/shop_fruits.csv";
    private static final String OUTPUT_FILE_PATH = "src/main/java/resources/fruits_report.csv";

    public static void main(String[] args) {
        Map<Operation, OperationStrategy> operationStrategyMap = new HashMap<>();
        operationStrategyMap.put(Operation.BALANCE, new AdditionStrategy());
        operationStrategyMap.put(Operation.SUPPLY, new AdditionStrategy());
        operationStrategyMap.put(Operation.RETURN, new AdditionStrategy());
        operationStrategyMap.put(Operation.PURCHASE, new ReduceStrategy());

        ServiceReader fileReader = new ServiceReaderImpl();
        FruitShopService fruitService = new FruitShopServiceImpl(operationStrategyMap);
        ParseToList parser = new Parser();
        List<FruitRecordDto> fruitRecordDtos = parser.parseToDto(
                fileReader.readFile(INPUT_FILE_PATH));
        fruitService.applyOperationOnFruitsDto(fruitRecordDtos);
        ReportCreator reportCreator = new ReportCreatorImpl();

        ServiceWriter fileWriter = new ServiceWriterImpl();
        String report = reportCreator.getFruitsReport(fileWriter,
                fruitService.getFruits());
        fileWriter.writeData(report, OUTPUT_FILE_PATH);
    }
}
