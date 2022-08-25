package core.basesyntax.writedata;

import core.basesyntax.report.DataReport;
import core.basesyntax.report.DataReportImpl;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class DataWritingImpl implements DataWriting {
    private DataReport dataReport;

    public void setDataReport(DataReport dataReport) {
        this.dataReport = dataReport;
    }

    @Override
    public void writeData(String fileReport) {
        setDataReport(new DataReportImpl());
        String[] strings = dataReport.reportOfData();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileReport, true))) {
            for (String data : strings) {
                writer.write(data + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to file"
                    + fileReport, e);
        }
    }
}
