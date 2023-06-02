package core.basesyntax.service;

import core.basesyntax.service.impl.TransactionParserServiceImpl;
import core.basesyntax.transaction.FruitTransaction;
import core.basesyntax.transaction.Operation;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionParserServiceImplTest {
    private TransactionParserService transactionParserService;

    @BeforeEach
    public void setup() {
        transactionParserService = new TransactionParserServiceImpl();
    }

    @Test
    public void parse_emptyRawData_notOk() {
        List<String> rawData = Collections.emptyList();
        List<FruitTransaction> parsedTransactions = transactionParserService.parse(rawData);
        Assertions.assertTrue(parsedTransactions.isEmpty());
    }

    @Test
    public void parse_nullRawData_notOk() {
        Assertions.assertThrows(NullPointerException.class,
                () -> transactionParserService.parse(null));
    }

    @Test
    void parse_validData_ok() {
        List<String> rawData = List.of("type,fruit,quantity","b,apple,100");
        List<FruitTransaction> expectedTransactions = List.of(
                new FruitTransaction(Operation.BALANCE, "apple", 100)
        );
        List<FruitTransaction> actualTransactions = transactionParserService.parse(rawData);
        Assertions.assertEquals(expectedTransactions, actualTransactions);
    }
}
