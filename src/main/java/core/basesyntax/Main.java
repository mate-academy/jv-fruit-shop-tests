package core.basesyntax;

import core.basesyntax.shopdao.FruitDaoImpl;
import core.basesyntax.shopoperations.Balance;
import core.basesyntax.shopoperations.ListOfOperations;
import core.basesyntax.shopoperations.Purchase;
import core.basesyntax.shopoperations.Return;
import core.basesyntax.shopoperations.ShopBalanceOperation;
import core.basesyntax.shopoperations.Supply;
import core.basesyntax.shopservice.CsvReadServiceImpl;
import core.basesyntax.shopservice.FileWriterServiceImpl;
import core.basesyntax.shopservice.ReportServiceImpl;
import core.basesyntax.shopservice.StoreService;
import core.basesyntax.shopservice.StoreServiceImpl;
import core.basesyntax.shopstrategy.StrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<ListOfOperations, ShopBalanceOperation> operationMap = new HashMap<>();
        operationMap.put(ListOfOperations.B, new Balance());
        operationMap.put(ListOfOperations.S, new Supply());
        operationMap.put(ListOfOperations.P, new Purchase());
        operationMap.put(ListOfOperations.R, new Return());
        StoreService storeService
                = new StoreServiceImpl(new StrategyImpl(operationMap), new FruitDaoImpl());
        List<String> data = new CsvReadServiceImpl().readFromFile("src/main/resources/file.csv");
        storeService.addToStorage(data);
        new FileWriterServiceImpl().writeToFile(new ReportServiceImpl(
                new FruitDaoImpl()).getReport(), "src/main/resources/report.csv");
    }
}
