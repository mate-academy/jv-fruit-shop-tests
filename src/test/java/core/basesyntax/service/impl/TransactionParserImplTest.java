package core.basesyntax.service.impl;

import static core.basesyntax.model.Product.APPLE;
import static core.basesyntax.model.Product.BANANA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TransactionParserImplTest {
    private static TransactionParser transactionParser;

    @BeforeAll
    static void beforeAll() {
        transactionParser = new TransactionParserImpl();
    }

    @DisplayName("Check correct input common data")
    @Test
    void getTransactions_validInput_ok() {
        List<String> inputTransactions = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        List<FruitTransaction> expectedList = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, BANANA.getName(), 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE.getName(), 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA.getName(), 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA.getName(), 13),
                new FruitTransaction(FruitTransaction.Operation.RETURN, APPLE.getName(), 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, APPLE.getName(),20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA.getName(), 5),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA.getName(), 50));
        assertEquals(expectedList, transactionParser.getTransactions(inputTransactions));
    }

    @DisplayName("Check only titles")
    @Test
    void getTransactions_onlyTitleLineInput_ok() {
        List<String> inputTransactions = List.of("type,fruit,quantity");
        assertTrue(transactionParser.getTransactions(inputTransactions).isEmpty());
    }

    @DisplayName("Check one data line")
    @Test
    void getTransactions_oneLineInput_ok() {
        List<String> inputTransactions = List.of("type,fruit,quantity", "b,banana,20");
        List<FruitTransaction> expectedList =
                List.of(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        BANANA.getName(), 20));
        assertEquals(expectedList, transactionParser.getTransactions(inputTransactions));
    }

    @DisplayName("Check empty list")
    @Test
    void getTransactions_emptyLineInput_ok() {
        List<String> inputTransactions = new ArrayList<>();
        assertTrue(transactionParser.getTransactions(inputTransactions).isEmpty());
    }

    @DisplayName("Check invalid input data - null-pointer")
    @Test
    void getTransactions_nullInput_notOk() {
        assertThrows(RuntimeException.class, () -> transactionParser.getTransactions(null));
    }

    @DisplayName("Check invalid input data - invalid format")
    @Test
    void getTransactions_invalidStringInputFormat_notOk() {
        List<String> inputTransactions = List.of("type,fruit", "b,banana");
        assertThrows(RuntimeException.class, ()
                -> transactionParser.getTransactions(inputTransactions));
    }
}
