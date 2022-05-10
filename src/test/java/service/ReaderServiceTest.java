package service;

import java.util.ArrayList;
import java.util.List;
import model.FruitTransaction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import service.impl.ReaderServiceImpl;

public class ReaderServiceTest {
    private static ReaderService readerService;
    private static final String INPUT_FILE = "src/test/resources/before.csv";
    private static final String NOT_EXISTS_INPUT_FILE = "src/tests/resources/before_not_exists.csv";

    @BeforeClass
    public static void beforeClass() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readerService_CorrectFile_OK() {
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(
                new FruitTransaction(FruitTransaction.Operation.getByValue("b"), "banana", 20));
        expected.add(
                new FruitTransaction(FruitTransaction.Operation.getByValue("b"), "apple", 100));
        expected.add(
                new FruitTransaction(FruitTransaction.Operation.getByValue("s"), "banana", 100));
        expected.add(
                new FruitTransaction(FruitTransaction.Operation.getByValue("p"), "banana", 13));
        expected.add(
                new FruitTransaction(FruitTransaction.Operation.getByValue("r"), "apple", 10));
        expected.add(
                new FruitTransaction(FruitTransaction.Operation.getByValue("p"), "apple", 20));
        expected.add(
                new FruitTransaction(FruitTransaction.Operation.getByValue("p"), "banana", 5));
        expected.add(
                new FruitTransaction(FruitTransaction.Operation.getByValue("s"), "banana", 50));
        List<FruitTransaction> actual = readerService.read(INPUT_FILE);
        Assert.assertEquals(actual, expected);
    }

    @Test(expected = RuntimeException.class)
    public void readerService_IncorrectFile_NotOK() {
        readerService.read(NOT_EXISTS_INPUT_FILE);
    }
}
