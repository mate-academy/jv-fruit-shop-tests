package core.basesyntax.servise;

import core.basesyntax.model.Fruit;
import core.basesyntax.servise.exception.IncorrectOperationException;
import core.basesyntax.servise.exception.InvalidInputDataException;
import core.basesyntax.servise.inrterfase.ReportBuilder;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportBuilderImplTest {
    private static final Fruit BANANA = new Fruit("banana");
    private static final Fruit APPLE = new Fruit("apple");
    private static List<FruitRecordDto> listFruitDto;
    private ReportBuilder splitter;

    @BeforeClass
    public static void beforeClass() throws Exception {
        listFruitDto = new ArrayList<>();
        listFruitDto.add(new FruitRecordDto(Operation.BALANCE, BANANA,20));
        listFruitDto.add(new FruitRecordDto(Operation.BALANCE, APPLE, 100));
        listFruitDto.add(new FruitRecordDto(Operation.SUPPLY, BANANA, 100));
        listFruitDto.add(new FruitRecordDto(Operation.PURCHASE, BANANA, 13));
        listFruitDto.add(new FruitRecordDto(Operation.RETURN, APPLE, 10));
        listFruitDto.add(new FruitRecordDto(Operation.PURCHASE, APPLE, 20));
        listFruitDto.add(new FruitRecordDto(Operation.PURCHASE, BANANA, 5));
        listFruitDto.add(new FruitRecordDto(Operation.SUPPLY, BANANA, 50));
    }

    @Before
    public void setUp() throws Exception {
        splitter = new ReportBuilderImpl();
    }

    @Test
    public void splitOfReport_getMapFruitRecordeDto_Ok() {
        List<String> inputData = new ArrayList<>();
        inputData.add("type,fruit,quantity");
        inputData.add("b,banana,20");
        inputData.add("b,apple,100");
        inputData.add("s,banana,100");
        inputData.add("p,banana,13");
        inputData.add("r,apple,10");
        inputData.add("p,apple,20");
        inputData.add("p,banana,5");
        inputData.add("s,banana,50");
        List<FruitRecordDto> actualResult = splitter.getReport(inputData);
        Assert.assertEquals(listFruitDto, actualResult);
    }

    @Test (expected = InvalidInputDataException.class)
    public void splitOfReport_InputNegativeNumber_NotOk() {
        List<String> inputData = new ArrayList<>();
        inputData.add("type,fruit,quantity");
        inputData.add("b,banana,20");
        inputData.add("b,apple,-100");
        splitter.getReport(inputData);

    }

    @Test (expected = InvalidInputDataException.class)
    public void splitOfReport_InputWrongNumberOfData_NotOk() {
        List<String> inputData = new ArrayList<>();
        inputData.add("type,fruit,quantity");
        inputData.add("b,banana,20,505");
        inputData.add("b,apple,100");
        splitter.getReport(inputData);
    }

    @Test (expected = IncorrectOperationException.class)
    public void splitOfReport_InputWrongTypeOfOperation_NotOk() {
        List<String> inputData = new ArrayList<>();
        inputData.add("type,fruit,quantity");
        inputData.add("b,banana,20");
        inputData.add("o,apple,100");
        splitter.getReport(inputData);
    }

    @Test (expected = NumberFormatException.class)
    public void splitOfReport_InputWrongTypeAsWord_NotOk() {
        List<String> inputData = new ArrayList<>();
        inputData.add("type,fruit,quantity");
        inputData.add("b,banana,20");
        inputData.add("o,apple,hundred");
        splitter.getReport(inputData);
    }

    @Test (expected = InvalidInputDataException.class)
    public void splitOfReport_InputEmptyFile_NotOk() {
        List<String> inputData = new ArrayList<>();
        splitter.getReport(inputData);
    }
}
