package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.service.DataFromOrderProcessor;
import core.basesyntax.service.ReaderFromFile;
import core.basesyntax.service.ReportCreator;
import core.basesyntax.service.impl.DataFromOrderProcessorImpl;
import core.basesyntax.service.impl.ReaderFromFileImpl;
import core.basesyntax.service.impl.ReportCreatorImpl;
import core.basesyntax.service.impl.ReportWriterImpl;
import java.util.List;

public class Main {
    private static final String FILE_NAME = "order.csv";
    private static final String REPORT_FILE_NAME = "report.csv";

    public static void main(String[] args) {
        ReaderFromFile readFromFile = new ReaderFromFileImpl();
        DataFromOrderProcessor processDataFromOrder = new DataFromOrderProcessorImpl();
        ReportCreator createTheReport = new ReportCreatorImpl();
        core.basesyntax.service.ReportWriter writeTheReportToDB = new ReportWriterImpl();

        final List<String> read = readFromFile.read(FILE_NAME);
        final List<String[]> split = processDataFromOrder.split(read);
        createTheReport.add(split);
        writeTheReportToDB.write(Storage.fruits, REPORT_FILE_NAME);
    }
}
