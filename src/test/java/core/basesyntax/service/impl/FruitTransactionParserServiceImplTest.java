package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.FruitTransactionException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionParserService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitTransactionParserServiceImplTest {
    private static final String TRANSACTION_REGEX = "\\w,\\w+,\\d+";
    private static FruitTransactionParserService parserService;

    @BeforeAll
    static void beforeAll() {
        parserService = new FruitTransactionParserServiceImpl();
    }

    @Test
    void test_stringToFruitTransactions_ok() {
        List<String> strings = List.of("b,banana,20","s,banana,10","p,banana,13","r,banana,2");
        List<FruitTransaction> transactionList = parserService.stringToFruitTransactions(strings);
        assertEquals(4,transactionList.size(), "size should be 4");
        List<FruitTransaction> fruitTransactions =
                FruitTransactionCreator.createValidTransactions();
        for (int i = 0; i < transactionList.size(); i++) {
            assertEquals(fruitTransactions.get(i), transactionList.get(i),
                    "transaction differ at index: " + i);
        }
    }

    @Test
    void test_stringToFruitTransactions_nonValidOperation_notOk() {
        List<String> nonValidStrings = List.of("q,banana,100");
        FruitTransactionException exception = assertThrows(FruitTransactionException.class,
                () -> parserService.stringToFruitTransactions(nonValidStrings));
        assertEquals("unsupported operator: q",exception.getMessage());
    }

    @Test
    void test_stringToFruitTransactions_nonValidOrder_notOk() {
        List<String> nonValidStrings = List.of("banana,r,100");
        FruitTransactionException exception = assertThrows(FruitTransactionException.class,
                () -> parserService.stringToFruitTransactions(nonValidStrings));
        String expectedMessage = "Transaction: " + nonValidStrings.get(0)
                + " is not valid, must match: " + TRANSACTION_REGEX;
        assertEquals(expectedMessage,exception.getMessage());
    }

    @Test
    void test_stringToFruitTransactions_negativeQuantity_notOk() {
        List<String> nonValidStrings = List.of("r,banana,-5");
        FruitTransactionException exception = assertThrows(FruitTransactionException.class,
                () -> parserService.stringToFruitTransactions(nonValidStrings),
                "quantity can not be negative");
        String expectedMessage = "Transaction: " + nonValidStrings.get(0)
                + " is not valid, must match: " + TRANSACTION_REGEX;
        assertEquals(expectedMessage,exception.getMessage());
    }
}

