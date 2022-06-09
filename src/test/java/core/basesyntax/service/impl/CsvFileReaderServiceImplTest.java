package core.basesyntax.service.impl;

import static core.basesyntax.model.FruitTransaction.Operation;
import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FileReaderService;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CsvFileReaderServiceImplTest {
    private static final FileReaderService fileReaderService = new CsvFileReaderServiceImpl();
    private static final List<FruitTransaction> expectedFruitTransactions = List.of(
            new FruitTransaction(Operation.BALANCE, new Fruit("banana", 20)),
            new FruitTransaction(Operation.BALANCE, new Fruit("apple", 100)),
            new FruitTransaction(Operation.SUPPLY, new Fruit("banana", 100)),
            new FruitTransaction(Operation.PURCHASE, new Fruit("banana", 13)),
            new FruitTransaction(Operation.RETURN, new Fruit("apple", 10)),
            new FruitTransaction(Operation.PURCHASE, new Fruit("apple", 20)),
            new FruitTransaction(Operation.PURCHASE, new Fruit("banana", 5)),
            new FruitTransaction(Operation.SUPPLY, new Fruit("banana", 50)),
            new FruitTransaction(Operation.SUPPLY, new Fruit("orange", 100)),
            new FruitTransaction(Operation.RETURN, new Fruit("melon", 20)));

    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void readTransactionsFromFile_nullFilePath_NotOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("filePath cannot be null");
        fileReaderService.readTransactionsFromFile(null);
    }

    @Test
    public void readTransactionsFromFile_NoInputFile_NotOk() {
        String filePath = "src/test/resources/";
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Can't get data from file: " + filePath);
        fileReaderService.readTransactionsFromFile(filePath);
    }

    @Test
    public void readTransactionsFromFile_validInputData_Ok() {
        List<FruitTransaction> actualResult = fileReaderService.readTransactionsFromFile(
                "src/test/resources/inputValidData.csv");
        assertEquals(expectedFruitTransactions, actualResult);
    }

    @Test
    public void readTransactionsFromFile_emptyRowsInInput_Ok() {
        List<FruitTransaction> actualResult1 = fileReaderService.readTransactionsFromFile(
                "src/test/resources/inputEmptyRows.csv");
        assertEquals(expectedFruitTransactions, actualResult1);
        List<FruitTransaction> actualResult2 = fileReaderService.readTransactionsFromFile(
                "src/test/resources/inputAllEmptyRows.csv");
        assertEquals(0, actualResult2.size());
    }

    @Test
    public void readTransactionsFromFile_emptyInputFile_Ok() {
        List<FruitTransaction> actualResult1 = fileReaderService.readTransactionsFromFile(
                "src/test/resources/inputEmptyFile.csv");
        assertEquals(0, actualResult1.size());
        List<FruitTransaction> actualResult2 = fileReaderService.readTransactionsFromFile(
                "src/test/resources/inputEmptyFileWithHeader.csv");
        assertEquals(0, actualResult2.size());
    }

    @Test
    public void readTransactionsFromFile_emptyFileName_Ok() {
        List<FruitTransaction> actualResult = fileReaderService.readTransactionsFromFile(
                "src/test/resources/.csv");
        assertEquals(expectedFruitTransactions, actualResult);
    }

    @Test
    public void readTransactionsFromFile_invalidFruitOperationInInput_NotOk() {
        final String operation = "x";
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("No such fruit operation type: " + operation);
        fileReaderService.readTransactionsFromFile("src/test/resources/inputInvalidOperation.csv");
    }

    @Test
    public void readTransactionsFromFile_emptyFruitOperationInInput_NotOk() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Fruit operation type cannot be empty");
        fileReaderService.readTransactionsFromFile("src/test/resources/inputEmptyOperation.csv");
    }

    @Test
    public void readTransactionsFromFile_emptyFruitNameInInput_NotOk() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Fruit name cannot be empty");
        fileReaderService.readTransactionsFromFile("src/test/resources/inputEmptyName.csv");
    }

    @Test
    public void readTransactionsFromFile_emptyFruitQuantityInInput_NotOk() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Fruit quantity cannot be empty");
        fileReaderService.readTransactionsFromFile("src/test/resources/inputEmptyQuantity.csv");
    }

    @Test
    public void readTransactionsFromFile_emptyAllFruitFieldsInInput_NotOk() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Fruit quantity cannot be empty");
        fileReaderService.readTransactionsFromFile("src/test/resources/inputEmptyAllFields.csv");
    }
}
