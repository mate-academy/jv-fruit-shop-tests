package core.basesyntax.fruitshop;

import core.basesyntax.fruitshop.dao.FruitStorageDao;
import core.basesyntax.fruitshop.dao.FruitStorageDaoImpl;
import core.basesyntax.fruitshop.model.Fruit;
import core.basesyntax.fruitshop.model.dto.FruitOperationDto;
import core.basesyntax.fruitshop.service.FruitDtoParser;
import core.basesyntax.fruitshop.service.FruitDtoParserImpl;
import core.basesyntax.fruitshop.service.FruitService;
import core.basesyntax.fruitshop.service.FruitServiceImpl;
import core.basesyntax.fruitshop.service.ReportService;
import core.basesyntax.fruitshop.service.ReportServiceImpl;
import core.basesyntax.fruitshop.service.shopoperation.OperationHandler;
import core.basesyntax.fruitshop.service.shopoperation.OperationType;
import core.basesyntax.fruitshop.service.shopoperation.StorageDecreaseHandler;
import core.basesyntax.fruitshop.service.shopoperation.StorageIncreaseHandler;
import core.basesyntax.fruitshop.util.ReadService;
import core.basesyntax.fruitshop.util.ReadServiceImpl;
import core.basesyntax.fruitshop.util.WriteService;
import core.basesyntax.fruitshop.util.WriteServiceImpl;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        FruitStorageDao fruitStorageDao = new FruitStorageDaoImpl();
        OperationHandler storageIncreaseHandler = new StorageIncreaseHandler(
                fruitStorageDao);
        Map<OperationType, OperationHandler> operationMap = new HashMap<>();
        operationMap.put(OperationType.BALANCE, storageIncreaseHandler);
        operationMap.put(OperationType.SUPPLY, storageIncreaseHandler);
        operationMap.put(OperationType.RETURN, storageIncreaseHandler);
        operationMap.put(OperationType.PURCHASE, new StorageDecreaseHandler(
                fruitStorageDao));

        ReadService readService = new ReadServiceImpl();
        List<String> fruitDataLines = readService.readFromFile("src/main/resources/input.csv");
        FruitDtoParser fruitDtoParser = new FruitDtoParserImpl();
        List<FruitOperationDto> fruitOperationDtoList = fruitDtoParser.parse(fruitDataLines);
        FruitService fruitService = new FruitServiceImpl(operationMap);

        for (FruitOperationDto fruitOperationDto : fruitOperationDtoList) {
            fruitService.getOperation(fruitOperationDto.getOperationType())
                    .apply(fruitOperationDto);
        }
        Set<Map.Entry<Fruit, BigDecimal>> report = new FruitStorageDaoImpl()
                .getDataReportFromStorage();
        ReportService reportService = new ReportServiceImpl();
        String reportString = reportService.generateReport(report);
        WriteService writeService = new WriteServiceImpl();
        writeService.writeToFile(reportString, "src/main/resources/output.csv");
    }
}
