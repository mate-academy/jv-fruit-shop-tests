package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverterService;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterServiceImplTest {
    private static final List<String> operationsValid1 =
            readFileContents("src/test/resources/operations_valid_1.csv");
    private static final List<String> operationsValid2 =
            readFileContents("src/test/resources/operations_valid_2.csv");
    private static final List<String> operationsEmpty =
            readFileContents("src/test/resources/operations_empty.csv");
    private static final List<String> operationsMixedFields =
            readFileContents("src/test/resources/operations_mixed_fields.csv");
    private static final List<String> operationsRandomFields =
            readFileContents("src/test/resources/operations_random_text.csv");
    private static final List<String> operationsWrongRegex =
            readFileContents("src/test/resources/operations_random_text.csv");
    private static DataConverterService dataConverter = new DataConverterServiceImpl();

    private FruitTransaction transaction1;
    private FruitTransaction transaction2;
    private FruitTransaction transaction3;
    private FruitTransaction transaction4;
    private List<FruitTransaction> transactions;

    @BeforeEach
    void setUp() {
        transaction1 = new FruitTransaction();
        transaction2 = new FruitTransaction();
        transaction3 = new FruitTransaction();
        transaction4 = new FruitTransaction();

        transactions = List.of(transaction1, transaction2, transaction3, transaction4);
    }

    @Test
    void getListOfTransactions_validOperationsSingleFruit_Ok() {
        transaction1.setOperation(FruitTransaction.Operation.BALANCE);
        transaction1.setQuantity(100);
        transaction1.setFruit("apple");

        transaction2.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction2.setQuantity(50);
        transaction2.setFruit("apple");

        transaction3.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction3.setQuantity(30);
        transaction3.setFruit("apple");

        transaction4.setOperation(FruitTransaction.Operation.RETURN);
        transaction4.setQuantity(5);
        transaction4.setFruit("apple");

        List<FruitTransaction> actual = dataConverter.getListOfTransactions(operationsValid1);

        for (int i = 0; i < 4; i++) {
            assertEquals(transactions.get(i), actual.get(i));
        }
    }

    @Test
    void getListOfTransactions_validOperationsManyFruits_Ok() {
        transaction1.setOperation(FruitTransaction.Operation.BALANCE);
        transaction1.setQuantity(20);
        transaction1.setFruit("banana");

        transaction2.setOperation(FruitTransaction.Operation.BALANCE);
        transaction2.setQuantity(100);
        transaction2.setFruit("apple");

        transaction3.setOperation(FruitTransaction.Operation.BALANCE);
        transaction3.setQuantity(50);
        transaction3.setFruit("orange");

        transaction4.setOperation(FruitTransaction.Operation.BALANCE);
        transaction4.setQuantity(5990);
        transaction4.setFruit("pear");

        List<FruitTransaction> actual = dataConverter.getListOfTransactions(operationsValid2);

        for (int i = 0; i < 4; i++) {
            assertEquals(transactions.get(i), actual.get(i));
        }
    }

    @Test
    void getListOfTransactions_emptyOperations_Ok() {
        List<FruitTransaction> actual = dataConverter.getListOfTransactions(operationsEmpty);

        assertTrue(actual.isEmpty());
    }

    @Test
    void getListOfTransactions_mixedFieldsOperations_NotOk() {
        assertThrows(NoSuchElementException.class,
                () -> dataConverter.getListOfTransactions(operationsMixedFields));
    }

    @Test
    void getListOfTransactions_wrongRegexOperations_NotOk() {
        assertThrows(IndexOutOfBoundsException.class,
                () -> dataConverter.getListOfTransactions(operationsWrongRegex));
    }

    @Test
    void getListOfTransactions_randomTextOperations_NotOk() {
        assertThrows(IndexOutOfBoundsException.class,
                () -> dataConverter.getListOfTransactions(operationsRandomFields));
    }

    private static List<String> readFileContents(String path) {
        Path pathOfFile = Path.of(path);

        try (BufferedReader reader = Files.newBufferedReader(pathOfFile)) {
            return reader.lines().toList();
        } catch (IOException e) {
            throw new RuntimeException("Can't read file. " + pathOfFile, e);
        }
    }
}
