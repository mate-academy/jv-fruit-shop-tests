import core.basesyntax.dao.WorkWithStorageImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.convert.ConvertData;
import core.basesyntax.service.convert.ConvertDataImpl;
import core.basesyntax.service.filework.AddDataToFileImpl;
import core.basesyntax.service.filework.GetDataFromFile;
import core.basesyntax.service.filework.GetDataFromFileImpl;
import core.basesyntax.strategy.HandlersStore;
import core.basesyntax.strategy.StrategyImplementation;
import core.basesyntax.strategy.StrategyImplementationImpl;
import core.basesyntax.strategy.handler.BalancesHandlerImpl;
import core.basesyntax.strategy.handler.Handler;
import core.basesyntax.strategy.handler.PurchaseHandlerImpl;
import core.basesyntax.strategy.handler.ReturnHandlerImpl;
import core.basesyntax.strategy.handler.SupplyHandlerImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FruitMain {
    private static final String PATH_TO_DATA = "src/main/resources/fileFuringDay.csv";
    private static final String PATH_TO_REPORT = "src/main/resources/fileReport.csv";

    public static void main(String[] args) {
        GetDataFromFile getDataService = new GetDataFromFileImpl();
        List<String> data = getDataService.getFromStorage(PATH_TO_DATA);
        ConvertData convertData = new ConvertDataImpl();
        List<FruitTransaction> dataConvert = convertData.convert(data);
        StrategyImplementation implementStrategy =
                new StrategyImplementationImpl(new HandlersStore(addPatternHandler()));
        implementStrategy.strategy(dataConvert);
        WorkWithStorageImpl storageService = new WorkWithStorageImpl();
        AddDataToFileImpl addDataService = new AddDataToFileImpl();
        addDataService.addInStorage(storageService.getAllFromStorage(),PATH_TO_REPORT);
    }

    public static Map<FruitTransaction.Operation, Handler> addPatternHandler() {
        Map<FruitTransaction.Operation,Handler> temporaryMap = new HashMap<>();
        temporaryMap.put(FruitTransaction.Operation.BALANCE,new BalancesHandlerImpl());
        temporaryMap.put(FruitTransaction.Operation.PURCHASE,new PurchaseHandlerImpl());
        temporaryMap.put(FruitTransaction.Operation.SUPPLY,new SupplyHandlerImpl());
        temporaryMap.put(FruitTransaction.Operation.RETURN,new ReturnHandlerImpl());
        return temporaryMap;
    }
}

