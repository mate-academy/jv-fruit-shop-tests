package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParser;
import core.basesyntax.service.impl.TransactionParserImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionParserImplTest {
    private static final List<FruitTransaction> transactionsList = List.of(
            new FruitTransaction("b", "banana", 120),
            new FruitTransaction("s", "apple", 50),
            new FruitTransaction("p", "apple", 13),
            new FruitTransaction("r", "banana", 10));
    private TransactionParser transactionParser;

    @BeforeEach
    void setUp() {
        transactionParser = new TransactionParserImpl();
    }

    @Test
    void parse_validCase_Ok() {
        List<String> data = new ArrayList<>();
        data.add("b,banana,120");
        data.add("s,apple,50");
        data.add("p,apple,13");
        data.add("r,banana,10");
        List<FruitTransaction> actual = transactionParser.parse(data);
        Assertions.assertEquals(transactionsList, actual);
    }

    @Test
    void parse_nullData_notOk() {
        Assert.assertThrows(RuntimeException.class, () ->
                transactionParser.parse(null));
    }

    @Test
    void parse_emptyFile_notOk() {
        Assert.assertThrows(RuntimeException.class, () ->
                transactionParser.parse(Collections.emptyList()));
    }
}
