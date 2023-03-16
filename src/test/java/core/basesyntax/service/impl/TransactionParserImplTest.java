package core.basesyntax.service.impl;

import core.basesyntax.model.TransactionDto;
import core.basesyntax.service.TransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TransactionParserImplTest {
    private static final List<String> INVALID_DATA = List.of("type,fruit", "s,apple");
    private static final List<String> DATA_FROM_FILE = List.of(
            "type,fruit,quantity",
            "b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50"
    );
    private TransactionParser transactionParser;

    @Before
    public void setUp() {
        transactionParser = new TransactionParserImpl();
    }

    @Test
    public void transactionParser_ok() {
        List<TransactionDto> expected = List.of(
                new TransactionDto(TransactionDto.Operation.BALANCE, "banana", 20),
                new TransactionDto(TransactionDto.Operation.BALANCE, "apple", 100),
                new TransactionDto(TransactionDto.Operation.SUPPLY, "banana", 100),
                new TransactionDto(TransactionDto.Operation.PURCHASE, "banana", 13),
                new TransactionDto(TransactionDto.Operation.RETURN, "apple", 10),
                new TransactionDto(TransactionDto.Operation.PURCHASE, "apple", 20),
                new TransactionDto(TransactionDto.Operation.PURCHASE, "banana", 5),
                new TransactionDto(TransactionDto.Operation.SUPPLY, "banana", 50)
        );
        List<TransactionDto> actual = transactionParser.parserTransactionOperation(DATA_FROM_FILE);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void trancsctionParser_ArgumentNull_notOk() {
        transactionParser.parserTransactionOperation(null);
    }

    @Test(expected = NullPointerException.class)
    public void transactionParser_ArgumentIsEmpty_notOk() {
        transactionParser.parserTransactionOperation(new ArrayList<>());
    }

    @Test(expected = RuntimeException.class)
    public void transactionParser_InvalidData_notOk() {
        transactionParser.parserTransactionOperation(INVALID_DATA);
    }
}
