package core.basesyntax.fruitshop.parser;

import core.basesyntax.fruitshop.model.Fruit;
import core.basesyntax.fruitshop.model.OperationType;
import core.basesyntax.fruitshop.model.RecordDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DtoCreatorImplTest {
    private DtoCreatorImpl dtoCreator;
    private List<String> inputData;
    private List<RecordDto> recordDtos;

    @Before
    public void setUp() throws Exception {
        dtoCreator = new DtoCreatorImpl();
        inputData = new ArrayList<>();
        recordDtos = new ArrayList<>();
        inputData.add("type,fruit,quantity");
        inputData.add(" b,banana,10");
        inputData.add("b,apple, 0");
        inputData.add("s,banana,100");
        inputData.add(" p,banana,13");
        inputData.add("r,pineapple,10");
    }

    @Test
    public void toDtoDataFormatter_Ok() {
        RecordDto firstdto = new RecordDto();
        firstdto.setOperationType(OperationType.BALANCE);
        firstdto.setFruitType(new Fruit("banana"));
        firstdto.setAmount(10);
        RecordDto secondDto = new RecordDto(OperationType.BALANCE, new Fruit("apple"),0);
        RecordDto thirdDto = new RecordDto(OperationType.SUPPLY, new Fruit("banana"), 100);
        RecordDto fourthDto = new RecordDto(OperationType.PURCHASE, new Fruit("banana"),13);
        RecordDto fifthDto = new RecordDto(OperationType.RETURN, new Fruit("pineapple"),10);
        recordDtos.add(firstdto);
        recordDtos.add(secondDto);
        recordDtos.add(thirdDto);
        recordDtos.add(fourthDto);
        recordDtos.add(fifthDto);
        Assert.assertEquals(recordDtos, dtoCreator.toDtoDataFormatter(inputData));
    }
}
