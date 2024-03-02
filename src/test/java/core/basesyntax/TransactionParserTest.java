package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.impl.FruitsDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FileReader;
import core.basesyntax.service.TransactionParser;
import core.basesyntax.service.impl.FileReaderImpl;
import core.basesyntax.service.impl.TransactionParserImpl;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class TransactionParserTest {
    private static List<FruitTransaction> expected;
    private final FileReader fileReader = new FileReaderImpl();
    private final TransactionParser transactionParser = new TransactionParserImpl();
    private final FruitsDao fruitsDao = new FruitsDaoImpl();

    @AfterEach
    void tearDown() {
        fruitsDao.storageAccess().clear();
    }

    {
        expected = new ArrayList<>();
        expected.add(new FruitTransaction("b", "banana", 100));
        expected.add(new FruitTransaction("p", "banana", 20));
        expected.add(new FruitTransaction("b", "apple", 200));
        expected.add(new FruitTransaction("b", "orange", 50));
        expected.add(new FruitTransaction("p", "orange", 10));
        expected.add(new FruitTransaction("p", "apple", 25));
        expected.add(new FruitTransaction("s", "orange", 10));
        expected.add(new FruitTransaction("s", "apple", 10));
        expected.add(new FruitTransaction("s", "kiwi", 150));
        expected.add(new FruitTransaction("r", "apple", 15));
    }

    @Test
    void parse_Transaction_isOk() {
        File inputDataFile = new File("src/test/resources/isOkParseTransactionFile.csv");
        List<String> fruitTransaction = fileReader.readFile(inputDataFile);
        List<FruitTransaction> actual = transactionParser.parse(fruitTransaction);
        assertEquals(expected, actual);
    }
}
