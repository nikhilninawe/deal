package manager.impl;

import au.com.bytecode.opencsv.CSVReader;
import entities.CurrencyDealCount;
import entities.Deal;
import entities.DealFile;
import entities.InvalidData;
import executor.DealExecutor;
import manager.DealManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import repository.CurrencyDealCountRepository;
import repository.DealFileRepository;
import repository.DealRepository;
import repository.InvalidDataRepository;

import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by 14577 on 03/06/17.
 */
@Component
public class DealManagerImpl implements DealManager{

    private static Logger logger = LoggerFactory.getLogger(DealManagerImpl.class);

    @Autowired
    DealRepository dealRepository;

    @Autowired
    CurrencyDealCountRepository currencyDealCountRepository;

    @Autowired
    InvalidDataRepository invalidDataRepository;

    @Autowired
    DealFileRepository dealFileRepository;

    @Autowired
    ExecutorService executorService;

    @Override
    public void processFile(MultipartFile file) throws Exception {
        Long start = System.currentTimeMillis();
        if(!validFileName(file.getOriginalFilename())){
            logger.error("File already exists");
            throw new Exception("File already exists");
        }

        DealFile dealFile = new DealFile(file.getOriginalFilename(), Calendar.getInstance().getTime(), "");
        dealFileRepository.save(dealFile);
        CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()));
        String[] nextLine;
        HashMap<String, Long> currrencyCountMap = new HashMap<>();
        List<Future<List<Deal>>> list = new ArrayList<>();

        int i = 0;
        List<String[]> dealLines = new ArrayList<>();
        while ((nextLine = reader.readNext()) != null) {
            if(nextLine == null){
                break;
            }
            if(i == 100){
                DealExecutor executor = new DealExecutor(dealLines, dealFile, dealRepository, invalidDataRepository);
                list.add(executorService.submit(executor));
                i = 0;
                dealLines = new ArrayList<>();
            }
            dealLines.add(nextLine);
            i++;
        }
        if(!CollectionUtils.isEmpty(dealLines)){
            DealExecutor executor = new DealExecutor(dealLines, dealFile, dealRepository, invalidDataRepository);
            list.add(executorService.submit(executor));
        }

        for(Future<List<Deal>> fut : list){
            for(Deal d : fut.get()) {
                currrencyCountMap.merge(d.getOrderingCurrency(),1L, (oldValue, one) -> oldValue + one);
            }
        }
        logger.info("Completed processing {}", (System.currentTimeMillis() - start));
        start = System.currentTimeMillis();
        summarize(currrencyCountMap, dealFile);
        logger.info("Summarize took time {} ms", (System.currentTimeMillis() - start));
    }

    public boolean validFileName(String fileName){
        return dealFileRepository.findByFileName(fileName) == null;
    }

    public void summarize(Map<String, Long> currencyCountMap, DealFile dealFile){
        Long invalidData = currencyCountMap.get(null);
        final AtomicLong validData = new AtomicLong();
        currencyCountMap.keySet()
                        .parallelStream()
                        .forEach(x -> {
                            CurrencyDealCount currencyDealCount = currencyDealCountRepository.findByCurrencyCode(x);
                            if(currencyDealCount == null){
                                currencyDealCount = new CurrencyDealCount(x, 0L);
                            }
                            currencyDealCount.setDealCount(currencyDealCount.getDealCount() + currencyCountMap.get(x));
                            currencyDealCountRepository.save(currencyDealCount);
                            validData.getAndAdd(currencyCountMap.get(x));
                        });


//        for(Deal currency : currencyCountMap.keySet()){
//            CurrencyDealCount currencyDealCount = currencyDealCountRepository.findByCurrencyCode(currency.getOrderingCurrency());
//            if(currencyDealCount == null){
//                currencyDealCount = new CurrencyDealCount(currency.getOrderingCurrency(), 0L);
//            }
//            currencyDealCount.setDealCount(currencyDealCount.getDealCount() + currencyCountMap.get(currency));
//            currencyDealCountRepository.save(currencyDealCount);
//            validData += currencyCountMap.get(currency);
//        }
        dealFile.setStatus("Valid records " + validData.get() + "; Invalid Records " + invalidData);
        dealFileRepository.save(dealFile);
    }

    @Override
    public String enquire(String fileName) {
        DealFile dealFile = dealFileRepository.findByFileName(fileName);
        if(dealFile == null){
            return "No Such file";
        }
        return dealFile.getStatus();
    }
}
