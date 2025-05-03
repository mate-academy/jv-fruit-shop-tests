package core.basesyntax;

import core.basesyntax.activity.Activities;
import core.basesyntax.activity.ActivityParser;
import core.basesyntax.activity.ActivityParserImpl;
import core.basesyntax.activity.ActivityStrategy;
import core.basesyntax.activity.ActivityStrategyImpl;
import core.basesyntax.fruit.dto.FruitDtoParser;
import core.basesyntax.fruit.dto.FruitDtoParserImpl;
import core.basesyntax.service.FileReader;
import core.basesyntax.service.FileReaderImpl;
import core.basesyntax.service.FileWriter;
import core.basesyntax.service.FileWriterImpl;
import core.basesyntax.service.ReportGeneratorImpl;
import core.basesyntax.service.handler.FruitHandler;
import core.basesyntax.service.handler.RemoveFruitHandlerImpl;
import core.basesyntax.service.handler.UpdateFruitHandlerImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        ActivityParser activityParser = new ActivityParserImpl();
        FruitDtoParser fruitDtoParser = new FruitDtoParserImpl();
        FileReader fileReader = new FileReaderImpl();

        Map<Activities, FruitHandler> handleGoodsMap = new HashMap<>();
        handleGoodsMap.put(Activities.BALANCE, new UpdateFruitHandlerImpl());
        handleGoodsMap.put(Activities.SUPPLY, new UpdateFruitHandlerImpl());
        handleGoodsMap.put(Activities.PURCHASE, new RemoveFruitHandlerImpl());
        handleGoodsMap.put(Activities.RETURN, new UpdateFruitHandlerImpl());
        ActivityStrategy activityStrategy = new ActivityStrategyImpl(handleGoodsMap);

        List<String> dataList = fileReader.read("src/main/resources/InputFile.csv");
        for (int i = 1; i < dataList.size(); i++) {
            activityStrategy.get(activityParser.parseActivity(dataList.get(i)))
                    .handleGoods(fruitDtoParser.parseFruitDto(dataList.get(i)));
        }
        FileWriter reportWriter = new FileWriterImpl();
        reportWriter.writeReport(new ReportGeneratorImpl().generateReport(),
                "src/main/resources/Report.csv");
    }
}
