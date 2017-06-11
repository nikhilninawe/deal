package repository;

import entities.CurrencyDealCount;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by 14577 on 04/06/17.
 */
@Repository
public interface CurrencyDealCountRepository extends CrudRepository<CurrencyDealCount, Long>{

    @Modifying
    @Transactional
    @Query("UPDATE CurrencyDealCount as a set a.dealCount = a.dealCount + :count where currencyCode = :currencyCode ")
    void incrementCount(@Param(value = "currencyCode") String currencyCode, @Param(value = "count") Long count);

    CurrencyDealCount findByCurrencyCode(String currencyCode);
}
