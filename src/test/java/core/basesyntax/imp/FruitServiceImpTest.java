package core.basesyntax.imp;

import core.basesyntax.model.FruitRecordDto;
import core.basesyntax.service.CustomFileReader;
import core.basesyntax.service.FruitOperation;
import core.basesyntax.service.FruitParse;
import core.basesyntax.service.FruitService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class FruitServiceImpTest {
    @Test
    public void makeReportRight_ok() {
        CustomFileReader reader = new CustomFileReadImp();
        List<String> list = reader.readFromFile("src/test/right.file.csv");
        FruitOperation add = new FruitAdd();
        Map<String, FruitOperation> map = new HashMap<>();
        map.put("s", add);
        map.put("r", add);
        map.put("b", add);
        map.put("p", new FruitMinus());
        FruitService fruitService = new FruitServiceImp(map);
        FruitParse parseImp = new FruitParseDtoParseImp();
        List<FruitRecordDto> listParse = parseImp.parse(list);
        parseImp.parse(list);
        fruitService.saveData(listParse);

        StringBuilder builder = new StringBuilder();
        builder.append("fruit").append(",").append("quantity")
                .append(System.lineSeparator())
                .append("banana,20").append(System.lineSeparator())
                .append("apple,100").append(System.lineSeparator());

        Assert.assertEquals(fruitService.makeReport(), String.valueOf(builder));
    }
}
