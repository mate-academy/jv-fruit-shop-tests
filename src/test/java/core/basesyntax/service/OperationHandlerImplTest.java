package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class OperationHandlerImplTest {
    private static final String INPUT_1_FILE_NAME = "input1.csv";
    private static final String INPUT_2_FILE_NAME = "input2.csv";
    private static final String INPUT_3_FILE_NAME = "input3.csv";
    private static FileReader fileReader;
    private static ParseService parseService;
    private static OperationStrategy operationStrategy;
    private static FruitDao fruitDao;
    private static ClassLoader classLoader;

    @BeforeAll
    static void beforeAll() {
        classLoader = FileReaderImplTest.class.getClassLoader();
        fileReader = new FileReaderImpl();
        parseService = new ParseServiceImpl();
        fruitDao = new FruitDaoImpl();
        operationStrategy = new OperationStrategyImpl(new HashMap<>() {{
                put(FruitTransaction.Operation.BALANCE, new RemnantOperationHandler());
                put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
                put(FruitTransaction.Operation.RETURN, new SupplyOperationHandler());
                put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
            }}
        );
    }

    @Test
    void handleInput1File_Ok() {
        handle(INPUT_1_FILE_NAME);
        assertEquals(FruitDaoImpl.getFruits().get("banana"), 152);
        assertEquals(FruitDaoImpl.getFruits().get("apple"), 90);
    }

    @Test
    void handleInput2File_Ok() {
        handle(INPUT_2_FILE_NAME);
        assertEquals(FruitDaoImpl.getFruits().get("banana"), 155);
        assertEquals(FruitDaoImpl.getFruits().get("apple"), 160);
    }

    @Test
    void handleInput3File_Ok() {
        handle(INPUT_3_FILE_NAME);
        assertEquals(FruitDaoImpl.getFruits().get("banana"), 115);
        assertEquals(FruitDaoImpl.getFruits().get("apple"), 160);
        assertEquals(FruitDaoImpl.getFruits().get("pear"), 140);
        fruitDao.remove("pear");
    }

    private void handle(String fileName) {
        URL resources = classLoader.getResource(fileName);
        List<String> readFromFile = fileReader.readFromFile(new File(resources.getFile()));
        List<FruitTransaction> fruitTransactions = parseService.parse(readFromFile);
        for (FruitTransaction fruitTransaction : fruitTransactions) {
            operationStrategy.get(fruitTransaction.getOperation())
                    .handle(fruitTransaction.getFruit(),
                            fruitTransaction.getQuantity());
        }
    }
}
