package core.basesyntax.servise;

import core.basesyntax.files.Reader;
import core.basesyntax.files.ReaderFileImpl;
import core.basesyntax.files.Writer;
import core.basesyntax.files.WriterFileImpl;
import core.basesyntax.model.FruitRecordDto;
import core.basesyntax.validation.LineValidator;
import core.basesyntax.validation.TitleValidator;
import core.basesyntax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String TEST_FILE = "src/main/resources/test.csv";
    private static final String TEST_LINE_01 = "type,fruit,quantity";
    private static final String TEST_LINE_02 = "b,banana,20";
    private static final String TEST_LINE_03 = "p,banana,5";
    private static final String EXPECT_FR02_TYPE = "b";
    private static final String EXPECT_FR02_FRUIT = "banana";
    private static final String EXPECT_FR02_QUANTITY = "20";
    private static final String EXPECT_FR03_TYPE = "p";
    private static final String EXPECT_FR03_FRUIT = "banana";
    private static final String EXPECT_FR03_QUANTITY = "5";
    private static List<String> testList;
    private static List<FruitRecordDto> expectedList;
    private static Reader reader;
    private static Writer writer;
    private static FruitRecordDto fr02;
    private static FruitRecordDto fr03;
    private static Validator titleValidator;
    private static Validator lineValidator;
    private static ReaderService readerService;

    @BeforeClass
    public static void beforeClass() {
        reader = new ReaderFileImpl(TEST_FILE);
        writer = new WriterFileImpl(TEST_FILE);
        fr02 = new FruitRecordDto();
        fr03 = new FruitRecordDto();
        fr02.setTypeOperation(EXPECT_FR02_TYPE);
        fr02.setFruit(EXPECT_FR02_FRUIT);
        fr02.setQuantity(EXPECT_FR02_QUANTITY);
        fr03.setTypeOperation(EXPECT_FR03_TYPE);
        fr03.setFruit(EXPECT_FR03_FRUIT);
        fr03.setQuantity(EXPECT_FR03_QUANTITY);
        expectedList = new ArrayList<>();
        expectedList.add(fr02);
        expectedList.add(fr03);
        testList = new ArrayList<>();
        testList.add(TEST_LINE_01);
        testList.add(TEST_LINE_02);
        testList.add(TEST_LINE_03);
        titleValidator = new TitleValidator();
        lineValidator = new LineValidator();
        readerService = new ReaderServiceImpl(reader, titleValidator, lineValidator);
        writer.write(testList);
    }

    @AfterClass
    public static void afterClass() {
        try {
            Files.delete(Path.of(TEST_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Can't delete file " + TEST_FILE);
        }
    }

    @Test
    public void readTestData_Ok() {
        List<FruitRecordDto> actualList = readerService.readData();
        Assert.assertEquals(expectedList, actualList);
    }
}
