package manager.impl;

import au.com.bytecode.opencsv.CSVReader;
import entities.Deal;
import entities.IsoCurrency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.print.attribute.standard.MediaSize;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 14577 on 03/06/17.
 */
@Component
public class CurrencyMangerImpl {

    Set<IsoCurrency> isoCurrencies;
    private static Logger logger = LoggerFactory.getLogger(CurrencyMangerImpl.class);


    @PostConstruct
    public void loadCurrencies() throws Exception{
        isoCurrencies = new HashSet<>();
        Resource resource = new ClassPathResource("currency_codes.csv");
        InputStream resourceInputStream = resource.getInputStream();
        CSVReader reader = new CSVReader(new InputStreamReader(resourceInputStream));
        String [] nextLine;
        reader.readNext(); //Skip first line
        while ((nextLine = reader.readNext()) != null) {
            String country = nextLine[0];
            String currencyCode = nextLine[2];
            IsoCurrency currency = new IsoCurrency(country, currencyCode);
            isoCurrencies.add(currency);
        }
        logger.info("Successfully imported ISO currencies");
    }

    public boolean validCurrency(String currencyCode){
        return isoCurrencies.contains(new IsoCurrency(currencyCode));
    }
}
