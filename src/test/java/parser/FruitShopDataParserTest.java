package parser;

import static org.junit.Assert.assertEquals;

import db.FruitsDao;
import db.GenericDao;
import db.Storage;
import exceptions.InvalidDataException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import models.Fruit;
import org.junit.After;
import org.junit.Test;
import strategy.AdditionalOperationHandler;
import strategy.OperationHandler;
import strategy.OperationStrategy;
import strategy.PurchaseOperationHandler;

public class FruitShopDataParserTest {
    private static GenericDao<Fruit, Integer> fruitsDao = new FruitsDao();
    private static Map<String, OperationHandler> map = Map.of(
            "b", new AdditionalOperationHandler(fruitsDao),
            "s", new AdditionalOperationHandler(fruitsDao),
            "r", new AdditionalOperationHandler(fruitsDao),
            "p", new PurchaseOperationHandler(fruitsDao));
    private static OperationStrategy strategy = new OperationStrategy(map);
    private static List<String> data;
    private static Parser<List<String>> parser = new FruitShopDataParser(fruitsDao, strategy);

    @After
    public void setUpStorageData() {
        Storage.fruits.clear();
    }

    @Test
    public void parseGoodData_Ok() {
        try {
            data = Files.readAllLines(Path.of("src/test/resources/CorrectTestSource.csv"));
        } catch (IOException e) {
            throw new RuntimeException("There is no source data like this.");
        }
        parser.parse(data);
        int counter = 0;
        for (Map.Entry<Fruit, Integer> entry : Storage.fruits.entrySet()) {
            System.out.println(entry.getKey().getName() + " " + entry.getValue());
            Fruit fruit = entry.getKey();
            //Нельзя просто гетнуть по имени,
            // так как мы используем фрукты, а они генерируются в хедлере
            if (fruit.getName().equals("ananas")
                    && entry.getValue() == 20) {
                counter++;
            }
            if (fruit.getName().equals("inzir")
                    && entry.getValue() == 30) {
                counter++;
            }
        }
        assertEquals(2, counter);
    }

    @Test(expected = InvalidDataException.class)
    public void parseBadData_NotOk() {
        try {
            data = Files.readAllLines(Path.of("src/test/resources/IncorrectTestSource.csv"));
        } catch (IOException e) {
            throw new RuntimeException("There is no source data like this.");
        }
        parser.parse(data);
    }
}
