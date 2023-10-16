package core.basesyntax.service.impl;

import core.basesyntax.service.TransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionParserImplTest {
    private static TransactionParser transactionParser;

    @BeforeAll
    static void beforeAll() {
        transactionParser = new TransactionParserImpl();
    }

    @Test
    void getTransactions_nullData_notOk() {
        List<String> data = null;
        Assertions.assertThrows(RuntimeException.class,
                () -> transactionParser.getTransactions(data),
                "If Data is Null should be Exception");
    }

    @Test
    void choiceTransaction_letterWrong_notOk() {
        List<String> data = new ArrayList<>();
        data.add("type,fruit,quantity");
        data.add("e,apple,45");
        Assertions.assertThrows(RuntimeException.class,
                () -> transactionParser.getTransactions(data),
                "Letter in String should be (r,b,p,s)");
    }
}
