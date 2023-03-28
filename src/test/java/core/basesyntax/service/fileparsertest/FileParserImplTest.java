package core.basesyntax.service.fileparsertest;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.fileparser.FileParser;
import core.basesyntax.service.fileparser.FileParserImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

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

        List<FruitTransaction> expected = new ArrayList<>();
        FruitTransaction fruitTransactionBanana = new FruitTransaction();
        fruitTransactionBanana.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransactionBanana.setFruit("banana");
        fruitTransactionBanana.setQuantity(20);
        expected.add(fruitTransactionBanana);

        FruitTransaction fruitTransactionApple = new FruitTransaction();
        fruitTransactionApple.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransactionApple.setFruit("apple");
        fruitTransactionApple.setQuantity(100);
        expected.add(fruitTransactionApple);

        List<FruitTransaction> parsedData = fileParser.parsedFruitTransactions(stringFruitTransactions);
        assertEquals(parsedData,expected);
    }
}
