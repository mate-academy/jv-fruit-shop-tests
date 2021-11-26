import dao.FruitDaoImpl;
import java.util.HashMap;
import java.util.List;
import model.Operation;
import service.FileWriterService;
import service.ReportService;
import service.action.ActionStrategyHandler;
import service.action.type.MinusQuantityHandler;
import service.action.type.PlusQuantityHandler;
import service.impl.FileReaderServiceImpl;
import service.impl.FileWriterServiceImpl;
import service.impl.ParserServiceImpl;
import service.impl.ReportServiceImpl;
import service.impl.UpdateStorageServiceImpl;
import service.impl.ValidatorServiceImpl;
import service.strategy.ActionStrategyImpl;

public class Main {
    private static final String INPUT_FILE_NAME = "src/main/resources/input.csv";
    private static final String OUTPUT_FILE_NAME = "src/main/resources/output.csv";

    public static void main(String[] args) {
        HashMap<Operation, ActionStrategyHandler> actionStrategyHashMap = new HashMap<>();
        actionStrategyHashMap.put(Operation.B, new PlusQuantityHandler(new FruitDaoImpl()));
        actionStrategyHashMap.put(Operation.S, new PlusQuantityHandler(new FruitDaoImpl()));
        actionStrategyHashMap.put(Operation.R, new PlusQuantityHandler(new FruitDaoImpl()));
        actionStrategyHashMap.put(Operation.P, new MinusQuantityHandler(new FruitDaoImpl()));

        UpdateStorageServiceImpl dataProcess = new UpdateStorageServiceImpl(
                        new ActionStrategyImpl(actionStrategyHashMap),
                        new ParserServiceImpl());
        FileReaderServiceImpl fileReader = new FileReaderServiceImpl();
        FileWriterService fileWriter = new FileWriterServiceImpl(
                new ReportServiceImpl(new FruitDaoImpl()));
        ValidatorServiceImpl validatorService = new ValidatorServiceImpl();
        ReportService reportService = new ReportServiceImpl(new FruitDaoImpl());

        List<String> listInput = fileReader.read(INPUT_FILE_NAME);
        validatorService.isValidData(listInput);
        dataProcess.updateStorageData(listInput);
        List<String> report = reportService.createReport();
        fileWriter.write(OUTPUT_FILE_NAME, report);
    }
}
