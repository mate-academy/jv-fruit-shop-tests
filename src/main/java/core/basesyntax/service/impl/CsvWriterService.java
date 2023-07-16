package core.basesyntax.service.impl;

import core.basesyntax.service.*;

import java.io.*;

public class CsvWriterService implements DataWriterService {

    @Override
    public void writeData(String reportData, String path) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))) {
            bufferedWriter.write(reportData);
        } catch (IOException e) {
            throw new RuntimeException("Can not write data to report. Path:" + path);
        }
    }
}
