package parser;

import db.FruitsDao;
import db.GenericDao;
import exceptions.InvalidDataException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import strategy.AdditionalOperationHandler;
import strategy.OperationHandler;
import strategy.OperationStrategy;
import strategy.PurchaseOperationHandler;

public class FruitShopDataParserTest {
    private static GenericDao fruitsDao = new FruitsDao();
    private static Map<String, OperationHandler> map = Map.of(
            "b", new AdditionalOperationHandler(fruitsDao),
            "s", new AdditionalOperationHandler(fruitsDao),
            "r", new AdditionalOperationHandler(fruitsDao),
            "p", new PurchaseOperationHandler(fruitsDao));
    private static OperationStrategy strategy = new OperationStrategy(map);
    private static List<String> data;
    private static Parser parser = new FruitShopDataParser(fruitsDao, strategy);

    @Test
    public void parseGoodData_Ok() {
        try {
            data = Files.readAllLines(Path.of("src/test/resources/CorrectTestSource.csv"));
        } catch (IOException e) {
            throw new RuntimeException("There is no source data like this.");
        }
        parser.parse(data);
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
