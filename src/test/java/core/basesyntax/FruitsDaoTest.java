package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.impl.FruitsDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FileReader;
import core.basesyntax.service.TransactionExecutor;
import core.basesyntax.service.TransactionParser;
import core.basesyntax.service.impl.FileReaderImpl;
import core.basesyntax.service.impl.TransactionExecutorImpl;
import core.basesyntax.service.impl.TransactionParserImpl;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class FruitsDaoTest {
    private final TransactionExecutor transactionExecutor = new TransactionExecutorImpl();
    private final FileReader fileReader = new FileReaderImpl();
    private final TransactionParser transactionParser = new TransactionParserImpl();
    private final FruitsDao fruitsDao = new FruitsDaoImpl();

    @AfterEach
    void tearDown() {
        fruitsDao.storageAccess().clear();
    }

    @Test
    void access_Storage_isOk() {
        File inputDataFile = new File("src/test/resources/accessStorageFile.csv");
        List<String> fruitsTransaction = fileReader.readFile(inputDataFile);
        List<FruitTransaction> fruits = transactionParser.parse(fruitsTransaction);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 154);
        expected.put("apple", 80);
        expected.put("kiwi", 50);
        expected.put("melon", 55);
        expected.put("test", 100);
        Map<String, Integer> actual = transactionExecutor.executeAll(fruits);
        assertEquals(expected, actual);
    }
}
