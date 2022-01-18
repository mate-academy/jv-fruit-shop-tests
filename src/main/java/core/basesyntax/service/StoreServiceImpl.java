package core.basesyntax.service;

import core.basesyntax.dao.MyFileReader;
import core.basesyntax.dao.MyFileWriter;
import core.basesyntax.dao.RemnantsDao;
import core.basesyntax.exceptions.ValidationException;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class StoreServiceImpl implements StoreService {

    private final RemnantsDao remnantsDao;
    private final PathService pathtService;
    private final MyFileReader myFileReader;
    private final MyFileWriter myFileWriter;
    private final OperationStrategy operationStrategy;

    public StoreServiceImpl(RemnantsDao remnantsDao,
                            PathService pathtService,
                            MyFileReader myFileReader,
                            MyFileWriter myFileWriter,
                            OperationStrategy operationStrategy) {
        this.remnantsDao = remnantsDao;
        this.pathtService = pathtService;
        this.myFileReader = myFileReader;
        this.myFileWriter = myFileWriter;
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void processTodaysInputFile() {
        DateService dateService = new DateServiceImpl();
        String currentDateString = dateService.getCurrentDateString();
        String fileFullPath = pathtService.getInputFullPath(currentDateString);
        File inputFile = new File(fileFullPath);
        List<String> recordsList = myFileReader.readDataFromFile(inputFile);
        if (recordsList.get(0).equals("type,fruit,quantity")) {
            recordsList.remove(0);
        }
        List<String[]> recordsSplittedList = recordsList.stream()
                .map(s -> s.split(","))
                .collect(Collectors.toList());
        for (String[] record: recordsSplittedList) { //внести изменения в Storage
            OperationTypes operationType = convertToOperationType(record[0]);
            try {
                operationStrategy.getOperationHandler(operationType).apply(record);
            } catch (ValidationException e) {
                throw new RuntimeException(
                "Exception in method getOperationHandler(operationType): unknown operationType "
                                + record[0]);
            }
        }
    }

    @Override
    public void generateTodaysReportFile() {
        DateService dateService = new DateServiceImpl();
        String currentDateString = dateService.getCurrentDateString();
        String reportFullPath = pathtService.getReportFullPath(currentDateString);
        List<String> reportList = remnantsDao.getRemnantsReportList();
        reportList.add(0, "fruit,quantity");
        myFileWriter.writeDataToFile(reportList, reportFullPath);
    }

    @Override
    public void createInputFileForNextDay() {
        DateService dateService = new DateServiceImpl();
        String nextDayDateString = dateService.getNextDayDateString();
        String nextInputFileFullPath = pathtService.getInputFullPath(nextDayDateString);
        List<String> reportList = remnantsDao.getRemnantsInputList();
        reportList.add(0, "type,fruit,quantity");
        myFileWriter.writeDataToFile(reportList, nextInputFileFullPath);
    }

    private OperationTypes convertToOperationType(String operation) {
        OperationTypes operationType;
        switch (operation) {
            case "b": //the remnants of fruits at the beginning of the working day
                operationType = OperationTypes.BALANCE;
                break;
            case "s": //means you are receiving new fruits from suppliers
                operationType = OperationTypes.SUPPLY;
                break;
            case "p": //means someone has bought some fruit
                operationType = OperationTypes.PURCHASE;
                break;
            case "r": //means someone who have bought the fruits now returns them back
                operationType = OperationTypes.RETURN;
                break;
            default:
                operationType = OperationTypes.UNKNOWN;
                break;
        }
        return operationType;
    }

}
