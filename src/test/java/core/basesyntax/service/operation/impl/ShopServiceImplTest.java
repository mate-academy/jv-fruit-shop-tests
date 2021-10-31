package core.basesyntax.service.operation.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.dao.ReaderService;
import core.basesyntax.dao.ReaderServiceImpl;
import core.basesyntax.dao.WriterService;
import core.basesyntax.dao.WriterServiceImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.operation.OperationStrategy;
import core.basesyntax.service.operation.ShopService;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ShopServiceImplTest {
    private FruitDao fruitDao;
    private OperationStrategy operationStrategy;
    private ShopService shopService;
    private ReaderService readerService;
    private WriterService writerService;
    private String fromFile = "src/main/java/resources/inputFile.csv";
    private String toFile = "src/main/java/resources/outputFile.csv";

    @Before
    public void setUp() {
        readerService = new ReaderServiceImpl();
        writerService = new WriterServiceImpl();
        fruitDao = new FruitDaoImpl();
        operationStrategy = new OperationStrategyImpl(fruitDao);
        shopService = new ShopServiceImpl(readerService,operationStrategy,writerService);
    }

    @Test
    public void getReport_Ok() {
        List<String> expected = new ArrayList<>();
        expected.add("fruit,quantity");
        List<String> actual = new ArrayList<>();
        actual.add("fruit,quantity");
        List<String> inputList = readerService.getDataFromFile(fromFile);
        for (String infoRow: inputList) {
            operationStrategy.get(infoRow.split(","));
        }
        StringBuilder stringBuilder = new StringBuilder("fruit,quantity");
        for (Fruit fruit: Storage.getFruits()) {
            stringBuilder.append("\n")
                    .append(fruit.getName())
                    .append(",")
                    .append(fruit.getQuantity());
        }
        writerService.writeData(toFile,stringBuilder.toString());
        expected.addAll(readerService.getDataFromFile(toFile));
        Storage.getFruits().clear();
        shopService.getReport(fromFile,toFile);
        actual.addAll(readerService.getDataFromFile(toFile));
        Assert.assertEquals(expected,actual);
    }

    @AfterClass
    public static void afterClass() {
        Storage.getFruits().clear();
    }
}
