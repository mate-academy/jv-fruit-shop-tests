package core.basesyntax.service.fileparsertest;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.fileparser.FileParser;
import core.basesyntax.service.fileparser.FileParserImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileParserImplTest {
    private static List<String> stringFruitTransactions;
    private static FileParser fileParser;

    @BeforeClass
    public static void beforeClass() {
        stringFruitTransactions = new ArrayList<>();
        fileParser = new FileParserImpl();
    }

    @Test
    public void parseInputData_Ok() {
        stringFruitTransactions.add("b,banana,20");
        stringFruitTransactions.add("b,apple,100");

        FruitTransaction fruitTransactionBanana = new FruitTransaction();
        fruitTransactionBanana.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransactionBanana.setFruit("banana");
        fruitTransactionBanana.setQuantity(20);
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(fruitTransactionBanana);

        FruitTransaction fruitTransactionApple = new FruitTransaction();
        fruitTransactionApple.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransactionApple.setFruit("apple");
        fruitTransactionApple.setQuantity(100);
        expected.add(fruitTransactionApple);

        List<FruitTransaction> parsedData =
                fileParser.parsedFruitTransactions(stringFruitTransactions);
        assertEquals(parsedData,expected);
    }
}
