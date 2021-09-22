package core.basesyntax;

import core.basesyntax.fruitshop.model.Fruit;
import core.basesyntax.fruitshop.model.OperationType;
import core.basesyntax.fruitshop.model.TransactionDto;
import core.basesyntax.fruitshop.service.FileReaderServiceImpl;
import core.basesyntax.fruitshop.service.FileWriterServiceImpl;
import core.basesyntax.fruitshop.service.FruitShopServiceImpl;
import core.basesyntax.fruitshop.service.OperationStrategyImpl;
import core.basesyntax.fruitshop.service.TransactionDtoServiceImpl;
import core.basesyntax.fruitshop.service.operation.BalanceOperationHandler;
import core.basesyntax.fruitshop.service.operation.OperationHandler;
import core.basesyntax.fruitshop.service.operation.PurchaseOperationHandler;
import core.basesyntax.fruitshop.service.operation.ReturnOperationHandler;
import core.basesyntax.fruitshop.service.operation.SupplyOperationHandler;
import core.basesyntax.fruitshop.storage.Storage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ServiceTest {
    private FileReaderServiceImpl readerService;
    private FileWriterServiceImpl fileWriterService;
    private FruitShopServiceImpl fruitShopService;
    private TransactionDtoServiceImpl transactionDtoService;

    @Before
    public void beforeAll() {
        Map<OperationType, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(OperationType.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(OperationType.SUPPLY, new SupplyOperationHandler());
        operationHandlerMap.put(OperationType.PURCHASE, new PurchaseOperationHandler());
        operationHandlerMap.put(OperationType.RETURN, new ReturnOperationHandler());
        OperationStrategyImpl operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        readerService = new FileReaderServiceImpl();
        fileWriterService = new FileWriterServiceImpl();
        fruitShopService = new FruitShopServiceImpl(operationHandlerMap);
        transactionDtoService = new TransactionDtoServiceImpl();
    }

    @Test
    public void readFileTest() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50");
        List<String> actual = readerService.readFile("src//main//resources//fruitShop.csv");
        Assert.assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void writeFileTest() {
        String path = "src//main//resources//reportFile.csv";
        String data = "fruit,quantity\nbanana,152\napple,90\n";
        String expected = "fruit,quantity\nbanana,152\napple,90\n";
        String actual;
        fileWriterService.writeToFile(data, path);
        try {
            Stream<String> lines = Files.lines(Paths.get(path));
            Assert.assertEquals(expected, lines.collect(Collectors.joining("\n", "", "\n")));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file" + e);
        }
    }

    @Test
    public void createReportIsOk() {
        Map<String, Integer> storage = new HashMap<>();
        Storage.fruitBalance.put("banana", 56);
        Storage.fruitBalance.put("apple", 73);
        Storage.fruitBalance.put("mango", 42);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,56" + System.lineSeparator()
                + "apple,73" + System.lineSeparator()
                + "mango,42" + System.lineSeparator();
        String actual = fruitShopService.createReport();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void createDto() {
        List<String> data = new ArrayList<>();
        data.add("type,fruit,quantity");
        data.add("b,banana,20");
        data.add("b,apple,100");
        data.add("s,banana,100");
        data.add("p,banana,13");
        data.add("r,apple,10");
        data.add("p,apple,20");
        data.add("p,banana,5");
        data.add("s,banana,50");
        List<TransactionDto> expected = new ArrayList<>();
        expected.add(new TransactionDto(OperationType.BALANCE, new Fruit("banana"), 20));
        expected.add(new TransactionDto(OperationType.BALANCE, new Fruit("apple"), 100));
        expected.add(new TransactionDto(OperationType.SUPPLY, new Fruit("banana"), 100));
        expected.add(new TransactionDto(OperationType.PURCHASE, new Fruit("banana"), 13));
        expected.add(new TransactionDto(OperationType.RETURN, new Fruit("apple"), 10));
        expected.add(new TransactionDto(OperationType.PURCHASE, new Fruit("apple"), 20));
        expected.add(new TransactionDto(OperationType.PURCHASE, new Fruit("banana"), 5));
        expected.add(new TransactionDto(OperationType.SUPPLY, new Fruit("banana"), 50));
        List<TransactionDto> actual = transactionDtoService.createDto(data);
        Assert.assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void applyOperationsOnTransactionDto() {
        List<TransactionDto> dtoList = new ArrayList<>();
        dtoList.add(new TransactionDto(OperationType.BALANCE, new Fruit("banana"), 20));
        dtoList.add(new TransactionDto(OperationType.BALANCE, new Fruit("apple"), 100));
        dtoList.add(new TransactionDto(OperationType.SUPPLY, new Fruit("banana"), 100));
        dtoList.add(new TransactionDto(OperationType.PURCHASE, new Fruit("banana"), 13));
        dtoList.add(new TransactionDto(OperationType.RETURN, new Fruit("apple"), 10));
        dtoList.add(new TransactionDto(OperationType.PURCHASE, new Fruit("apple"), 20));
        dtoList.add(new TransactionDto(OperationType.PURCHASE, new Fruit("banana"), 5));
        dtoList.add(new TransactionDto(OperationType.SUPPLY, new Fruit("banana"), 50));
        Map<String, Integer> expected = new HashMap<>();
        expected.put(new Fruit("banana").getName(), 152);
        expected.put(new Fruit("apple").getName(), 90);
        fruitShopService.applyOperationsOnTransactionDto(dtoList);
        Map<String, Integer> actual = Storage.fruitBalance;
        Assert.assertEquals(expected.entrySet().toArray(), actual.entrySet().toArray());
    }

    @After
    public void afterEachTest() {
        Storage.fruitBalance.clear();
    }
}
