package core.service.impl;

import core.dao.FruitDao;
import core.model.Fruit;
import core.model.TransactionDto;
import core.service.FruitService;
import core.strategy.Operation;
import core.strategy.OperationHandler;
import core.strategy.OperationStrategy;
import java.util.List;

public class FruitServiceImpl implements FruitService {
    private FruitDao fruitsDao;
    private OperationStrategy strategy;

    public FruitServiceImpl(FruitDao fruitsDao, OperationStrategy activitiesStrategy) {
        this.fruitsDao = fruitsDao;
        this.strategy = activitiesStrategy;
    }

    public List<Fruit> changeBalance(List<TransactionDto> transactions) {
        for (TransactionDto transactionDto: transactions) {
            OperationHandler activitiesService = strategy.get(Operation
                    .getOperationType(transactionDto.getOperation()));
            activitiesService.apply(transactionDto.getFruitName(),
                    transactionDto.getQuantity());
        }
        return fruitsDao.getAll();
    }
}
