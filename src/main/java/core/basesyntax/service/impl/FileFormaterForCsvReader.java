package core.basesyntax.service.impl;

import core.basesyntax.dao.CustomFileReader;
import java.util.ArrayList;
import java.util.List;

public class FileFormaterForCsvReader {
    private static final String DELIMITOR = ",";
    private final CustomFileReader fileReaderService;

    public FileFormaterForCsvReader(CustomFileReader fileReaderService) {
        this.fileReaderService = fileReaderService;
    }

    public List<String[]> parseCsv(String filePath) {
        String fileContent = fileReaderService.readFile(filePath);
        List<String[]> data = new ArrayList<>();
        String[] lines = fileContent.split(System.lineSeparator());
        for (String line : lines) {
            data.add(line.split(DELIMITOR));
        }
        return data;
    }
}
