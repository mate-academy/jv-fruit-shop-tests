package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.service.CreateTheReport;
import core.basesyntax.service.ProcessDataFromOrder;
import core.basesyntax.service.ReadFromFile;
import core.basesyntax.service.WriteTheReportToDataBase;
import core.basesyntax.service.impl.CreateTheReportImpl;
import core.basesyntax.service.impl.ProcessDataFromOrderImpl;
import core.basesyntax.service.impl.ReadFromFileImpl;
import core.basesyntax.service.impl.WriteTheReportToDataBaseImpl;
import java.util.List;

public class Main {
    private static final String FILE_NAME = "order.csv";
    private static final String REPORT_FILE_NAME = "report.csv";

    public static void main(String[] args) {
        ReadFromFile readFromFile = new ReadFromFileImpl();
        ProcessDataFromOrder processDataFromOrder = new ProcessDataFromOrderImpl();
        CreateTheReport createTheReport = new CreateTheReportImpl();
        WriteTheReportToDataBase writeTheReportToDB = new WriteTheReportToDataBaseImpl();

        final List<String> read = readFromFile.read(FILE_NAME);
        final List<String[]> split = processDataFromOrder.split(read);
        createTheReport.add(split);
        writeTheReportToDB.write(Storage.fruits, REPORT_FILE_NAME);
    }
}
