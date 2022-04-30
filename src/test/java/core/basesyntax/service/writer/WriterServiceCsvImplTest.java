package core.basesyntax.service.writer;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class WriterServiceCsvImplTest {
    private WriterService writer;

    @Before
    public void setUp() throws Exception {
        writer = new WriterServiceCsvImpl();
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_createFileWithEmptyFilePass_notOk() {
        List<FruitTransaction> fruits = new ArrayList<>();
        String outputFile = "";
        writer.writeToFile(outputFile, fruits);
    }

    @Test
    public void writeToFile_createValidFile_Ok() {
        List<FruitTransaction> fruits = new ArrayList<>();
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 60);
        FruitTransaction fruitTransaction1 =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100);
        fruits.add(fruitTransaction);
        fruits.add(fruitTransaction1);
        String outputFile = "createValidFileResult";
        writer.writeToFile(outputFile, fruits);
    }
}
