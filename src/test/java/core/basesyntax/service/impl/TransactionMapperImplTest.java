package core.basesyntax.service.impl;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.TransactionMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

class TransactionMapperImplTest {
    private static final Path VALID_SOURCE_0_PATH = Path.of("src/test/resources/validSource_0.csv");
    private static final Path VALID_SOURCE_1_PATH = Path.of("src/test/resources/validSource_1.csv");
    private static final String HEADER = "type,fruit,quantity";
    private static String[] invalidSourceLines;
    private static List<Transaction> validSource0Transactions;
    private static List<Transaction> validSource1Transactions;
    private static TransactionMapper mapper;

    @BeforeAll
    static void beforeAll() {

        mapper = new TransactionMapperImpl();
        validSource0Transactions = List.of(
                new Transaction(Transaction.Operation.BALANCE, "banana", 20),
                new Transaction(Transaction.Operation.BALANCE, "apple", 100),
                new Transaction(Transaction.Operation.SUPPLY, "banana", 100),
                new Transaction(Transaction.Operation.PURCHASE, "banana", 13),
                new Transaction(Transaction.Operation.RETURN, "apple", 10),
                new Transaction(Transaction.Operation.PURCHASE, "apple", 20),
                new Transaction(Transaction.Operation.PURCHASE, "banana", 5),
                new Transaction(Transaction.Operation.SUPPLY, "banana", 50));
        validSource1Transactions = List.of(
                new Transaction(Transaction.Operation.BALANCE, "pineapple", 20),
                new Transaction(Transaction.Operation.BALANCE, "coconut", 10),
                new Transaction(Transaction.Operation.BALANCE, "blueberry", 321),
                new Transaction(Transaction.Operation.BALANCE, "raspberry", 0),
                new Transaction(Transaction.Operation.SUPPLY, "pineapple", 11),
                new Transaction(Transaction.Operation.PURCHASE, "coconut", 9),
                new Transaction(Transaction.Operation.RETURN, "pineapple", 100000),
                new Transaction(Transaction.Operation.PURCHASE, "blueberry", 321),
                new Transaction(Transaction.Operation.RETURN, "raspberry", 1));
        invalidSourceLines = new String[]{"", ",,", ",banana,20", "_,banana,20",
                "23,banana,20", "p,,20", "p,_apple,20", "p,123,20", "p,banana777,20",
                "b,pineapple,", "b,pineapple,-20", "b,pineapple,_20", "b,pineapple,20sd"};
    }

    @Test
    void mapAll_nullSource_notOk() {
        Assertions.assertThrows(NullPointerException.class,
                () -> mapper.mapAll(null),
                "NulPointer exception must be thrown if source is null");
    }

    @Test
    void mapAll_emptyList_notOk() {
        List<String> emptyList = Collections.emptyList();
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> mapper.mapAll(emptyList),
                "IllegalArgumentException must be thrown if source is empty");
    }

    @Test
    void mapAll_validSource0_ok() throws IOException {
        List<String> source = Files.readAllLines(VALID_SOURCE_0_PATH);
        List<Transaction> result = mapper.mapAll(source);
        Assertions.assertEquals(validSource0Transactions, result);
        
    }

    @Test
    void mapAll_validSource1_ok() throws IOException {
        List<String> source = Files.readAllLines(VALID_SOURCE_1_PATH);
        List<Transaction> result = mapper.mapAll(source);
        Assertions.assertEquals(validSource1Transactions, result);
    }

    @TestFactory
    Stream<DynamicTest> mapAll_invalidSource_notOk() {
        return Arrays.stream(invalidSourceLines)
                .map(line -> DynamicTest.dynamicTest("Test data \"" + line + "\"",
                        () -> Assertions.assertThrows(IllegalArgumentException.class,
                                () -> mapper.mapAll(List.of(HEADER, line)))
                ));
    }
}
