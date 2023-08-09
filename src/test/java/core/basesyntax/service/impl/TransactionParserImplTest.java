package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.TransactionParser;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionParserImplTest {
    private static final List<String> INPUT_LIST = List.of(
            "b,apple,100",
            "r,apple,10",
            "s,apple,20",
            "p,apple,30");

    private static final List<FruitTransaction> EXPECTED_OUTPUT_LIST = List.of(
            new FruitTransaction(Operation.BALANCE,"apple",100),
            new FruitTransaction(Operation.RETURN,"apple",10),
            new FruitTransaction(Operation.SUPPLY,"apple",20),
            new FruitTransaction(Operation.PURCHASE,"apple",30));

    @BeforeEach
    void setUp() {
        Storage.fruitStorage.clear();
    }

    @Test
    void parseCsvRows_normalMode_Ok() {
        TransactionParser transactionParser = new TransactionParserImpl();

        List<FruitTransaction> actual = transactionParser.parseCsvRows(INPUT_LIST);

        Assertions.assertEquals(actual, EXPECTED_OUTPUT_LIST);
    }
}
