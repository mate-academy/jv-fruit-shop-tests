package core.basesyntax.service;

import core.basesyntax.service.impl.TransactionParserServiceImpl;
import core.basesyntax.transaction.FruitTransaction;
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
        List<String> rawData = List.of();
        List<FruitTransaction> parsedTransactions = transactionParserService.parse(rawData);
        Assertions.assertTrue(parsedTransactions.isEmpty());
    }
}
