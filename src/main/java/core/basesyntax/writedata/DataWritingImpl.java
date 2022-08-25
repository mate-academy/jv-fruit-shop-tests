package core.basesyntax.writedata;

import core.basesyntax.clear.DataClear;
import core.basesyntax.clear.DataClearImpl;
import core.basesyntax.report.DataReport;
import core.basesyntax.report.DataReportImpl;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class DataWritingImpl implements DataWriting {
    private DataReport dataReport;
    private DataClear dataClear;

    public void setDataClear(DataClear dataClear) {
        this.dataClear = dataClear;
    }

    public void setDataReport(DataReport dataReport) {
        this.dataReport = dataReport;
    }

    @Override
    public void writeData(String fileReport) {
        setDataClear(new DataClearImpl());
        setDataReport(new DataReportImpl());
        dataClear.clearDataIfExist(fileReport);
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
