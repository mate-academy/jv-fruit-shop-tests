package core.basesyntax;

import core.basesyntax.dao.report.ReportCreator;
import core.basesyntax.dao.report.ReportCreatorImpl;
import core.basesyntax.dao.transaction.Transaction;
import core.basesyntax.dao.transaction.TransactionImpl;
import core.basesyntax.service.FileReader;
import core.basesyntax.service.FileReaderImpl;
import core.basesyntax.service.FileWriter;
import core.basesyntax.service.FileWriterImpl;

public class Main {
    public static void main(String[] args) {
        FileWriter fileWriter = new FileWriterImpl();
        FileReader fileReader = new FileReaderImpl();
        Transaction transaction = new TransactionImpl();
        ReportCreator reportCreator = new ReportCreatorImpl();
        
        transaction.getTransactionList(fileReader.readFile("file.csv"));
        fileWriter.writeReport(reportCreator.createReport(),"report_file.csv");
    }
}
