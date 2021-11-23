package core.basesyntax;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.dao.ReaderServiceImpl;
import core.basesyntax.dao.WriterServiceImpl;
import core.basesyntax.service.operation.ShopService;
import core.basesyntax.service.operation.impl.OperationStrategyImpl;
import core.basesyntax.service.operation.impl.ShopServiceImpl;

public class Main {
    private static ShopService shopService = new ShopServiceImpl(new ReaderServiceImpl(),
            new OperationStrategyImpl(new FruitDaoImpl()), new WriterServiceImpl());

    public static void main(String[] args) {
        String fromFile = "src/main/java/resources/inputFile.csv";
        String toFile = "src/main/java/resources/outputFile.csv";
        shopService.getReport(fromFile,toFile);
    }
}
