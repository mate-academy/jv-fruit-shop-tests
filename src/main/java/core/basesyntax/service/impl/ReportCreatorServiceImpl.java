package core.basesyntax.service.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportCreatorService;
import java.util.List;
import java.util.stream.Collectors;

public class ReportCreatorServiceImpl implements ReportCreatorService {
    private final StorageDao storageDao;

    public ReportCreatorServiceImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public List<String> createReport() {
        return storageDao.getStorage().stream()
                .map(Fruit::getStock)
                .collect(Collectors.toList());
    }
}
