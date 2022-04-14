package core.basesyntax.servise;

import core.basesyntax.files.Reader;
import core.basesyntax.files.ReaderFileImpl;
import core.basesyntax.files.Writer;
import core.basesyntax.files.WriterFileImpl;
import core.basesyntax.lib.ActivitiesEnum;
import core.basesyntax.servise.activity.ActivityHandler;
import core.basesyntax.servise.activity.BalanceActivityHandlerImpl;
import core.basesyntax.servise.activity.PurchaseActivityHandlerImpl;
import core.basesyntax.servise.activity.ReturnActivityHandlerImpl;
import core.basesyntax.servise.activity.SupplyActivityHandlerImpl;
import core.basesyntax.validation.LineValidator;
import core.basesyntax.validation.TitleValidator;
import core.basesyntax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ShopServiceImplTest {
    private static final String SOURCE_FILE =
            "src/test/resources/test_source_shop_service_impl.csv";
    private static final String RESULT_FILE =
            "src/test/resources/test_result_shop_service_impl.csv";
    private static final String ACTUAL_LINE_01 = "fruit,quantity";
    private static final String ACTUAL_LINE_02 = "banana,152";
    private static final String ACTUAL_LINE_03 = "apple,90";
    private static List<String> expectedList;
    private static Map<String, ActivityHandler> activities;
    private static ActivityStrategy activityStrategy;
    private static Validator titleValidator;
    private static Validator lineValidator;
    private static Reader readerSource;
    private static Reader readerResult;
    private static ReaderService readerService;
    private static Writer writer;
    private static WriterService writerService;
    private static ShopService shopService;

    @BeforeClass
    public static void beforeClass() {
        expectedList = new ArrayList<>();
        expectedList.add(ACTUAL_LINE_01);
        expectedList.add(ACTUAL_LINE_02);
        expectedList.add(ACTUAL_LINE_03);
        activities = new HashMap<>();
        activities.put(ActivitiesEnum.BALANCE.getActivity(), new BalanceActivityHandlerImpl());
        activities.put(ActivitiesEnum.SUPPLY.getActivity(), new SupplyActivityHandlerImpl());
        activities.put(ActivitiesEnum.PURCHASE.getActivity(), new PurchaseActivityHandlerImpl());
        activities.put(ActivitiesEnum.RETURN.getActivity(), new ReturnActivityHandlerImpl());
        activityStrategy = new ActivityStrategyImpl(activities);
        titleValidator = new TitleValidator();
        lineValidator = new LineValidator();
        readerSource = new ReaderFileImpl(SOURCE_FILE);
        readerResult = new ReaderFileImpl(RESULT_FILE);
        writer = new WriterFileImpl(RESULT_FILE);
        readerService = new ReaderServiceImpl(readerSource, titleValidator, lineValidator);
        writerService = new WriterServiceImpl(writer);
        shopService = new ShopServiceImpl(readerService, writerService, activityStrategy);
    }

    @AfterClass
    public static void afterClass() {
        try {
            Files.delete(Path.of(RESULT_FILE));
        } catch (IOException e) {
            Assert.fail("Can't delete file " + RESULT_FILE);
        }
    }

    @Test
    public void readTestFileComputeWriteAndCheckResult_Ok() {
        shopService.availableFruitsAfterWorkShift();
        List<String> actualList = readerResult.read();
        Assert.assertEquals(actualList, expectedList);
    }
}
