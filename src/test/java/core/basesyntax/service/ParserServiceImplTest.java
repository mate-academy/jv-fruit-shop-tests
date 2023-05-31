package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.impl.ParserServiceImpl;
import core.basesyntax.strategy.handler.BalanceOperationHandler;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.PurchaseOperationHandler;
import core.basesyntax.strategy.handler.ReturnOperationHandler;
import core.basesyntax.strategy.handler.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserServiceImplTest {
    private static List<String> lines;
    private static Map<Operation, OperationHandler> handlerMap;
    private static ParserService parserService;

    @BeforeClass
    public static void beforeClass() {
        lines = new ArrayList<>();
        handlerMap = new HashMap<>();
        handlerMap.put(Operation.BALANCE, new BalanceOperationHandler());
        handlerMap.put(Operation.RETURN, new ReturnOperationHandler());
        handlerMap.put(Operation.SUPPLY, new SupplyOperationHandler());
        handlerMap.put(Operation.PURCHASE, new PurchaseOperationHandler());
        parserService = new ParserServiceImpl();
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void parse_emptyList_ok() {
        List<String> lines = new ArrayList<>();
        parserService.parseLines(lines).size();
    }

    @Test
    public void parse_resultTransaction_ok() {
        lines.add("type,fruit,quantity");
        lines.add("b,banana,20");
        lines.add("b,apple,100");
        lines.add("s,banana,100");
        lines.add("p,banana,13");
        lines.add("r,apple,10");
        lines.add("p,apple,20");
        lines.add("p,banana,5");
        lines.add("s,banana,50");
        List<Transaction> expected = new ArrayList<>();
        expected.add(new Transaction(Operation.BALANCE, new Fruit("banana"), 20));
        expected.add(new Transaction(Operation.BALANCE, new Fruit("apple"), 100));
        expected.add(new Transaction(Operation.SUPPLY, new Fruit("banana"), 100));
        expected.add(new Transaction(Operation.PURCHASE, new Fruit("banana"), 13));
        expected.add(new Transaction(Operation.RETURN, new Fruit("apple"), 10));
        expected.add(new Transaction(Operation.PURCHASE, new Fruit("apple"), 20));
        expected.add(new Transaction(Operation.PURCHASE, new Fruit("banana"), 5));
        expected.add(new Transaction(Operation.SUPPLY, new Fruit("banana"), 50));
        List<Transaction> actual = parserService.parseLines(lines);
        assertEquals(expected, actual);
    }
}
