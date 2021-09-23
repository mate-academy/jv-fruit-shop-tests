package core.basesyntax.servise;

import core.basesyntax.files.Reader;
import core.basesyntax.files.ReaderFileImpl;
import core.basesyntax.model.FruitRecordDto;
import core.basesyntax.validation.LineValidator;
import core.basesyntax.validation.TitleValidator;
import core.basesyntax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String TEST_FILE = "src/test/resources/test_reader_service_impl.csv";
    private static final String EXPECT_FR02_TYPE = "b";
    private static final String EXPECT_FR02_FRUIT = "banana";
    private static final String EXPECT_FR02_QUANTITY = "20";
    private static final String EXPECT_FR03_TYPE = "p";
    private static final String EXPECT_FR03_FRUIT = "banana";
    private static final String EXPECT_FR03_QUANTITY = "5";
    private static List<FruitRecordDto> expectedList;
    private static Reader reader;
    private static FruitRecordDto expectredFruitRecordDto01;
    private static FruitRecordDto expectredFruitRecordDto02;
    private static Validator titleValidator;
    private static Validator lineValidator;
    private static ReaderService readerService;

    @BeforeClass
    public static void beforeClass() {
        reader = new ReaderFileImpl(TEST_FILE);
        expectredFruitRecordDto01 = new FruitRecordDto();
        expectredFruitRecordDto02 = new FruitRecordDto();
        expectredFruitRecordDto01.setTypeOperation(EXPECT_FR02_TYPE);
        expectredFruitRecordDto01.setFruit(EXPECT_FR02_FRUIT);
        expectredFruitRecordDto01.setQuantity(EXPECT_FR02_QUANTITY);
        expectredFruitRecordDto02.setTypeOperation(EXPECT_FR03_TYPE);
        expectredFruitRecordDto02.setFruit(EXPECT_FR03_FRUIT);
        expectredFruitRecordDto02.setQuantity(EXPECT_FR03_QUANTITY);
        expectedList = new ArrayList<>();
        expectedList.add(expectredFruitRecordDto01);
        expectedList.add(expectredFruitRecordDto02);
        titleValidator = new TitleValidator();
        lineValidator = new LineValidator();
        readerService = new ReaderServiceImpl(reader, titleValidator, lineValidator);
    }

    @Test
    public void readTestData_Ok() {
        List<FruitRecordDto> actualList = readerService.readData();
        Assert.assertEquals(expectedList, actualList);
    }
}
