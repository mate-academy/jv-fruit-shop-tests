package core.basesyntax;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.model.FruitTransferDto;
import core.basesyntax.service.implementations.BalanceOperationHandlerImpl;
import core.basesyntax.service.implementations.PurchaseOperationHandlerImpl;
import core.basesyntax.service.implementations.ReadServiceImpl;
import core.basesyntax.service.implementations.ReportMakerImpl;
import core.basesyntax.service.implementations.ReturnOperationHandlerImpl;
import core.basesyntax.service.implementations.SupplyOperationHandlerImpl;
import core.basesyntax.service.implementations.TransferServiceImpl;
import core.basesyntax.service.implementations.ValidateImpl;
import core.basesyntax.service.implementations.WriteServiceImpl;
import core.basesyntax.service.inerfaces.OperationHandler;
import core.basesyntax.service.inerfaces.ReadService;
import core.basesyntax.service.inerfaces.ReportMaker;
import core.basesyntax.service.inerfaces.TransferService;
import core.basesyntax.service.inerfaces.Validate;
import core.basesyntax.service.inerfaces.WriteService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static final String PATH_FROM = "src/main/java/core/basesyntax/resources/FileFrom.csv";
    public static final String PATH_WHERE = "src/main/java/core/basesyntax/resources/FileWhere.csv";

    public static void main(String[] args) {
        ReadService readService = new ReadServiceImpl();
        Validate validator = new ValidateImpl();
        List<String> listOfProducts = readService.readFile(PATH_FROM);
        validator.validate(listOfProducts);
        FruitsDao fruitsDao = new FruitsDaoImpl();
        Map<String, OperationHandler> operationMap = new HashMap<>();
        operationMap.put("b", new BalanceOperationHandlerImpl(fruitsDao));
        operationMap.put("s", new SupplyOperationHandlerImpl(fruitsDao));
        operationMap.put("p", new PurchaseOperationHandlerImpl(fruitsDao));
        operationMap.put("r", new ReturnOperationHandlerImpl(fruitsDao));
        OperationStrategy strategy = new OperationStrategyImpl(operationMap);
        TransferService transferService = new TransferServiceImpl();
        List<FruitTransferDto> dtos = transferService.parse(listOfProducts);
        for (int i = 0; i < dtos.size(); i++) {
            FruitTransferDto dto = dtos.get(i);
            strategy.handle(dto);
        }
        ReportMaker maker = new ReportMakerImpl();
        WriteService writeService = new WriteServiceImpl();
        writeService.writeToFile(PATH_WHERE, maker.formReport());
    }
}
