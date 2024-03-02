package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class TransactionHandlerSupplyImplTest {
    private final TransactionExecutor transactionExecutor = new TransactionExecutorImpl();
    private final FileReader fileReader = new FileReaderImpl();
    private final TransactionParser transactionParser = new TransactionParserImpl();
    private final FruitsDao fruitsDao = new FruitsDaoImpl();

    @AfterEach
    void tearDown() {
        fruitsDao.storageAccess().clear();
    }

    @Test
    void supply_Quantity_NotOk() {
        File inputDataFile = new File("src/test/resources/notOkSupplyQuantityFile.csv");
        List<String> fruitsTransaction = fileReader.readFile(inputDataFile);
        List<FruitTransaction> fruits = transactionParser.parse(fruitsTransaction);
        assertThrows(RuntimeException.class, () -> transactionExecutor.executeAll(fruits));
    }
}
