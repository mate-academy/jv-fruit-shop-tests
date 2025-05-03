package core.basesyntax.service;

import core.basesyntax.Record;
import core.basesyntax.RecordParser;
import core.basesyntax.activity.Activities;
import core.basesyntax.activity.ActivityHandler;
import core.basesyntax.activity.ActivityStrategy;
import core.basesyntax.file.CsvFileReader;
import core.basesyntax.file.CsvFileReaderImpl;
import core.basesyntax.file.FileWriter;
import core.basesyntax.file.FileWriterFileImpl;
import core.basesyntax.implementation.ActivityStrategyImpl;
import core.basesyntax.implementation.BalanceActivityHandlerImpl;
import core.basesyntax.implementation.PurchaseActivityHandlerImpl;
import core.basesyntax.implementation.ReturnActivityHandlerImpl;
import core.basesyntax.implementation.SupplyActivityHandlerImpl;
import core.basesyntax.implementation.ValidatorImpl;
import core.basesyntax.implementation.WriterServiceImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceTest {
    private static final String INPUT_FILE = "src/test/resources/fruit_service_test_input.txt";
    private static final String OUTPUT_FILE = "src/test/resources/fruit_service_test_output.txt";
    private static final String EXPECTED
            = "fruit,quantity\n"
            + "banana,152\n"
            + "apple,90";
    private static FruitService fruitService;
    private static CsvFileReader csvReader;

    @BeforeClass
    public static void setUp() {
        Map<String, ActivityHandler> activities = new HashMap<>();
        activities.put(Activities.BALANCE.getActivity(), new BalanceActivityHandlerImpl());
        activities.put(Activities.SUPPLY.getActivity(), new SupplyActivityHandlerImpl());
        activities.put(Activities.PURCHASE.getActivity(), new PurchaseActivityHandlerImpl());
        activities.put(Activities.RETURN.getActivity(), new ReturnActivityHandlerImpl());
        ActivityStrategy activityStrategy = new ActivityStrategyImpl(activities);
        csvReader = new CsvFileReaderImpl();
        String inputData = csvReader.readFromFile(INPUT_FILE);
        RecordParser recordParser = new RecordParser();
        List<Record> recordList = recordParser.parseRecords(inputData);
        Validator validator = new ValidatorImpl();
        validator.validate(recordList);
        FileWriter fileWriter = new FileWriterFileImpl();
        WriterService writerService = new WriterServiceImpl(fileWriter, OUTPUT_FILE);
        fruitService =
                new FruitService(recordList, writerService, activityStrategy);
        fruitService.applyOperationsOnFruitsRecords();
    }

    @Test
    public void generateReport_ok() {
        fruitService.generateReport();
        String actual = csvReader.readFromFile(OUTPUT_FILE);
        Assert.assertEquals(actual, EXPECTED);
    }
}
