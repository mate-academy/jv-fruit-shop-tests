package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class CsvFilrImplServiceTest {
    private CsvFileService csvFileService = new CsvFilrImplService();
    private String correctFileFormat = "src/main/resources/fullreport.csv";
    private String wrongFileFormat = "src/main/resources/wrong.csv";
    private final List<FruitTransaction> expected = new ArrayList<>();

    @Test
    public void retunListFruitTransaction_OK() {
        expected.add(new FruitTransaction(FruitTransaction.Operation.getFruitOperation("b"),
                "banana",20));
        expected.add(new FruitTransaction(FruitTransaction.Operation.getFruitOperation("b"),
                "apple",100));
        expected.add(new FruitTransaction(FruitTransaction.Operation.getFruitOperation("s"),
                "banana",100));
        expected.add(new FruitTransaction(FruitTransaction.Operation.getFruitOperation("p"),
                "banana",13));
        expected.add(new FruitTransaction(FruitTransaction.Operation.getFruitOperation("r"),
                "apple",10));
        expected.add(new FruitTransaction(FruitTransaction.Operation.getFruitOperation("p"),
                "apple",20));
        expected.add(new FruitTransaction(FruitTransaction.Operation.getFruitOperation("p"),
                "banana",5));
        expected.add(new FruitTransaction(FruitTransaction.Operation.getFruitOperation("s"),
                "banana",50));
        List<FruitTransaction> actual = csvFileService.readFileToList(correctFileFormat);
        assertEquals(expected,actual);
    }

    @Test
    public void wrongFileFormat_NotOk() {
        List<FruitTransaction> actual = csvFileService.readFileToList(wrongFileFormat);
        assertTrue(actual.isEmpty());
    }
}

