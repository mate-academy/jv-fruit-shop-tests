package core.basesyntax.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitOperation;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionParser;
import core.basesyntax.service.impl.FruitTransactionParserImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitTransactionParcerTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private List<String> stringList;
    private List<FruitTransaction> fruitTransactions = new ArrayList<>();
    private FruitTransactionParser dataParserService = new FruitTransactionParserImpl();

    @BeforeEach
    public void setUp() {
        stringList = List.of("b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        fruitTransactions = List.of(
                new FruitTransaction(FruitOperation.BALANCE, BANANA, 20),
                new FruitTransaction(FruitOperation.BALANCE, APPLE, 100),
                new FruitTransaction(FruitOperation.SUPPLY, BANANA, 100),
                new FruitTransaction(FruitOperation.PURCHASE, BANANA, 13),
                new FruitTransaction(FruitOperation.RETURN, APPLE, 10),
                new FruitTransaction(FruitOperation.PURCHASE, APPLE, 20),
                new FruitTransaction(FruitOperation.PURCHASE, BANANA, 5),
                new FruitTransaction(FruitOperation.SUPPLY, BANANA, 50)
        );
        dataParserService = new FruitTransactionParserImpl();
    }

    @Test
    void parse_emptyInput_Ok() {
        List<FruitTransaction> fruitTransactions
                = dataParserService.parseFruitTransactions(new ArrayList<>());
        assertEquals(0, fruitTransactions.size());
    }

    @Test
    void parse_correctTransaction_Ok() {
        List<FruitTransaction> expectedTransactions = fruitTransactions;
        List<FruitTransaction> actualTransactions
                = dataParserService.parseFruitTransactions(stringList);
        assertEquals(expectedTransactions.size(), actualTransactions.size());
        assertEquals(expectedTransactions, actualTransactions);
    }
}
