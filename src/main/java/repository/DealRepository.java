package repository;

import entities.CurrencyDealCount;
import entities.Deal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 14577 on 03/06/17.
 */
@Repository
public interface DealRepository extends CrudRepository<Deal, Long>{

//    @Query(value = "select new entities.CurrencyDealCount(orderingCurrency, count(d)) " +
//                   " from Deal as d " +
//            "where sourceFileName = :fileName" +
//            " group by orderingCurrency")
//    List<CurrencyDealCount> findBySourceFileName(@Param(value = "fileName") String fileName);
//
//    @Transactional(readOnly = true)
//    Long countBySourceFileName(String fileName);
//
//    @Transactional(readOnly = tr)
//    Long countByDealId(String dealId);

}
