package core.basesyntax;

import core.basesyntax.dao.ReaderService;
import core.basesyntax.dao.ReaderServiceImp;
import core.basesyntax.dao.WriterService;
import core.basesyntax.dao.WriterServiceImp;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.ReportServiceImp;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ReaderService readerService = new ReaderServiceImp();
        WriterService writerService = new WriterServiceImp();
        ReportService reportService = new ReportServiceImp();
        String infilePath = "src/main/resources/activities_day_one.csv";
        List<String> strings = readerService.readFromFile(infilePath);
        List<String> reportList = reportService.makeBalanceReport(strings);
        String outfilePath = "src/main/reports/";
        writerService.writeToFile(reportList, outfilePath);
    }
}
