package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.file.FileWriter;
import core.basesyntax.service.validator.DataValidator;
import java.util.Map;

public class ReportServiceImpl implements ReportService {
    public static final String COLUMNS = "type,quantity";

    private FileWriter fileWriter;
    private FruitsDao fruitsDao;

    public ReportServiceImpl(FileWriter fileWriter, FruitsDao fruitsDao) {
        this.fileWriter = fileWriter;
        this.fruitsDao = fruitsDao;
    }

    @Override
    public void createReport(String filename) {
        StringBuilder builder = new StringBuilder(COLUMNS);
        for (Map.Entry<Fruit, Integer> entry : fruitsDao.getAll()) {
            builder.append(System.lineSeparator())
                    .append(entry.getKey().getType())
                    .append(DataValidator.CSV_SEPARATOR)
                    .append(entry.getValue());
        }
        fileWriter.writeToFile(filename, builder.toString());
    }
}
