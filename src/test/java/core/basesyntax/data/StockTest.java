package core.basesyntax.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.service.FruitMapper;
import core.basesyntax.service.FruitMapperImpl;
import core.basesyntax.strategy.TransactionProcessor;
import core.basesyntax.strategy.TransactionProcessorImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class StockTest {
    private Stock stock = new Stock();

    @BeforeAll
    static void beforeAll() {
    }

    @BeforeEach
    void clear() {
        stock.clear();
    }

    @Test
    public void addElementsToStorage() {
        List<String> lines = new ArrayList<String>();
        lines.add("type,fruit,quantity");
        lines.add("b,banana,20");
        lines.add("b,apple,90");
        lines.add("s,banana,100");
        lines.add("p,banana,40");
        lines.add("s,apple,10");
        FruitMapper fruitMapper = new FruitMapperImpl();
        List<FruitTransaction> fruitTransactions = fruitMapper.mapData(lines);
        TransactionProcessor transactionProcessor = new TransactionProcessorImpl(stock);
        Map<String, Integer> expected = Map.of(
                "banana", 80,
                "apple", 100
        );
        transactionProcessor.process(fruitTransactions);
        Map<String, Integer> actual = stock.getData();
        assertEquals(expected, actual);
    }

    @Test
    public void getData_Ok() {
        stock.putData("banana", 80);
        stock.putData("apple", 120);
        Map<String, Integer> expected = Map.of(
                "banana", 80,
                "apple", 120
        );
        assertEquals(expected, stock.getData());
    }

    @Test
    public void getData_containsKey_Ok() {
        stock.putData("banana", 80);
        stock.putData("apple", 120);
        Map<String, Integer> expected = Map.of(
                "banana", 80,
                "apple", 120
        );
        assertEquals(expected.containsKey("banana"),
                stock.getData().containsKey("banana"));
    }

    @Test
    public void getData_containsKey_notOk() {
        stock.putData("apple", 120);
        assertFalse(stock.getData().containsKey("banana"));
    }

    @Test
    public void getData_getValue_Ok() {
        stock.putData("banana", 80);
        stock.putData("apple", 120);
        Map<String, Integer> expected = Map.of(
                "banana", 80,
                "apple", 120
        );
        assertEquals(expected.get("banana"), stock.getData().get("banana"));
    }

    @Test
    public void getData_containsValue_notOk() {
        stock.putData("apple", 120);
        assertFalse(stock.getData().containsValue(80));
    }

    @Test
    public void getData_getValue_notOk() {
        stock.putData("apple", 120);
        assertNotEquals(80, (int) stock.getData().get("apple"));
    }
}
