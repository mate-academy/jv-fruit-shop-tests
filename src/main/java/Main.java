import dao.StorageDao;
import dao.StorageDaoImpl;
import java.util.List;
import model.FruitTransaction;
import service.OperationHandlerStrategy;
import service.OperationHandlerStrategyImpl;
import service.OperationService;
import service.OperationServiceImpl;
import service.ParseService;
import service.ParseServiceImpl;
import service.ReadFileService;
import service.ReadFileServiceImpl;
import service.ReportService;
import service.ReportServiceImpl;
import service.WriteFileServiceImpl;

public class Main {
    private static final String FROM_FILE = "src/main/resources/input.csv";
    private static final String TO_FILE = "src/main/resources/report.csv";

    public static void main(String[] args) {
        ReadFileService readService = new ReadFileServiceImpl();
        List<String> readFile = readService.read(FROM_FILE);
        ParseService parseService = new ParseServiceImpl();
        List<FruitTransaction> infoFromFile = parseService.getInfo(readFile);
        StorageDao storageDao = new StorageDaoImpl();
        OperationHandlerStrategy operationHandlerStrategy = new OperationHandlerStrategyImpl();
        OperationService operationService = new OperationServiceImpl(operationHandlerStrategy);
        operationService.calculate(infoFromFile);
        ReportService reportService = new ReportServiceImpl(storageDao);
        String reportedInformation = reportService.createReport();
        new WriteFileServiceImpl().write(reportedInformation, TO_FILE);
    }
}
