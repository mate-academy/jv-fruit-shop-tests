package shop;

import java.util.HashMap;
import java.util.List;
import shop.dao.FruitDao;
import shop.dao.FruitDaoImpl;
import shop.model.ActionType;
import shop.service.Reader;
import shop.service.ReportService;
import shop.service.UpdateDbService;
import shop.service.Validator;
import shop.service.Writer;
import shop.service.action.ActionHandler;
import shop.service.action.ActionStrategyHandler;
import shop.service.action.DecreaseActionHandler;
import shop.service.action.IncreaseActionHandler;
import shop.service.impl.ActionStrategyHandlerImpl;
import shop.service.impl.CsvReaderImpl;
import shop.service.impl.CsvWriterImpl;
import shop.service.impl.ReportServiceImpl;
import shop.service.impl.UpdateDbServiceImpl;
import shop.service.impl.ValidatorImpl;

public class FruitShop {
    private static final String INPUT_FILE_NAME = "src\\main\\resources\\input.csv";
    private static final String OUTPUT_FILE_NAME = "src\\main\\resources\\output.csv";

    public static void main(String[] args) {
        FruitDao fruitDao = new FruitDaoImpl();
        HashMap<String, ActionHandler> actionMap = new HashMap<>();
        actionMap.put(ActionType.BALANCE.getAlias(), new IncreaseActionHandler(fruitDao));
        actionMap.put(ActionType.SUPPLY.getAlias(), new IncreaseActionHandler(fruitDao));
        actionMap.put(ActionType.RETURN.getAlias(), new IncreaseActionHandler(fruitDao));
        actionMap.put(ActionType.PURCHASE.getAlias(), new DecreaseActionHandler(fruitDao));

        ActionStrategyHandler actionStrategyHandler = new ActionStrategyHandlerImpl(actionMap);
        UpdateDbService updateDbService = new UpdateDbServiceImpl(actionStrategyHandler);
        Reader reader = new CsvReaderImpl();
        Writer writer = new CsvWriterImpl();
        Validator validator = new ValidatorImpl();
        ReportService reportService = new ReportServiceImpl(fruitDao);

        List<String> parsedData = reader.read(INPUT_FILE_NAME);
        if (validator.valid(parsedData)) {
            updateDbService.updateStorage(parsedData);
            writer.write(reportService.makeReport(), OUTPUT_FILE_NAME);
        }
    }

}
