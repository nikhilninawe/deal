package executor;

import entities.Deal;
import entities.DealFile;
import entities.InvalidData;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import repository.DealRepository;
import repository.InvalidDataRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by 14577 on 04/06/17.
 */

public class DealExecutor implements Callable<List<Deal>> {

    private static Logger logger = LoggerFactory.getLogger(DealExecutor.class);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    DealRepository dealRepository;
    InvalidDataRepository invalidDataRepository;

    private List<String[]> rows;
    private DealFile dealFile;

    public DealExecutor(){}

    public DealExecutor(List<String[]> row, DealFile fileName, DealRepository dealRepository, InvalidDataRepository invalidDataRepository){
        this.rows = row;
        this.dealFile = fileName;
        this.dealRepository = dealRepository;
        this.invalidDataRepository = invalidDataRepository;
    }

    @Override
    public List<Deal> call(){
        List<Deal> dealToSave = new ArrayList<>();
        for(String[] row : rows) {
            if (!validRow(row)) {
                logger.error("Validation failed");
                InvalidData invalidData = new InvalidData(StringUtils.join(row, ","), dealFile);
                invalidDataRepository.save(invalidData);
                continue;
            }
            String dealId = row[0];
            String orderingCurrency = row[1];
            String toCurrency = row[2];
            Date timeStamp = null;
            try {
                timeStamp = dateFormat.parse(row[3]);
            } catch (ParseException ex) {
                logger.error("Date format exception occurred");
                continue;
            }
            Double amountInOrderingCurrency = Double.parseDouble(row[4]);
            Deal deal = new Deal(dealId, orderingCurrency, toCurrency, timeStamp, amountInOrderingCurrency, dealFile);
            dealToSave.add(deal);
        }
        if(!CollectionUtils.isEmpty(dealToSave)) {
            dealRepository.save(dealToSave);
        }
        return dealToSave;
    }

//    private boolean validDeal(Deal deal){
//        return currencyManger.validCurrency(deal.getOrderingCurrency()) &&
//                currencyManger.validCurrency(deal.getToCurrency()) &&
//                dealRepository.countByDealId(deal.getDealId()) == 0;
//
//    }

    private boolean validRow(String[] row){
        if(row.length < 5){
            return false;
        }
        for(String columnValue : row){
            if(StringUtils.isEmpty(columnValue)){
                return false;
            }
        }
        return true;
    }
}
