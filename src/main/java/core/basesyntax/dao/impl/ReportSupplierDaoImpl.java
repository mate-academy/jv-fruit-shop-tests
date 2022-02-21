package core.basesyntax.dao.impl;

import core.basesyntax.dao.ReportSupplierDao;
import core.basesyntax.dao.ReportWriterDao;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.Fruit;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;

public class ReportSupplierDaoImpl implements ReportSupplierDao, ReportWriterDao {
    private final StorageDao storageDao;

    public ReportSupplierDaoImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public List<String> get() {
        return storageDao.getStorage().stream()
                .map(Fruit::getStock)
                .collect(Collectors.toList());
    }

    @Override
    public void createReport(String filePath) {
        File reportFile = new File(filePath);
        try {
            reportFile.delete();
            reportFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("FAILED TO CREATE: cant create new file: "
                    + System.lineSeparator() + filePath);
        }
        writeStringToFile(reportFile, ("fruit,quantity" + System.lineSeparator()));
        get().forEach(line -> writeStringToFile(reportFile, (line + System.lineSeparator())));
    }

    public void writeStringToFile(File file, String line) {
        try {
            Files.write(file.toPath(), line.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("FAILED TO WRITE: cant write line: "
                    + line + ", to file: "
                    + System.lineSeparator() + file.getName());
        }

    }
}
