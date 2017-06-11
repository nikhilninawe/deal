package main;

import au.com.bytecode.opencsv.CSVReader;
import entities.IsoCurrency;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.util.FileCopyUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by 14577 on 04/06/17.
 */
public class GenerateData {

    static List<IsoCurrency> isoCurrencies = new ArrayList<>();
    static Random randomGenerator = new Random();
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private static Writer writer;


    public void main(String[] args) throws IOException{
        writer = new BufferedWriter( new FileWriter("output.csv"));

        InputStream is = GenerateData.class.getResourceAsStream("/currency_codes.csv");
        CSVReader reader = new CSVReader(new InputStreamReader(is));
        String [] nextLine;
        reader.readNext(); //Skip first line
        while ((nextLine = reader.readNext()) != null) {
            String country = nextLine[0];
            String currencyCode = nextLine[2];
            if(StringUtils.isEmpty(currencyCode)){
                continue;
            }
            IsoCurrency currency = new IsoCurrency(country, currencyCode);
            isoCurrencies.add(currency);
        }
        for (int i=0; i<100000; i++){
            String dealId = UUID.randomUUID().toString();
            String fromCurrency = isoCurrencies.get(randomGenerator.nextInt(isoCurrencies.size())).getCurrencyCode();
            String toCurrency = isoCurrencies.get(randomGenerator.nextInt(isoCurrencies.size())).getCurrencyCode();
            while (toCurrency.equalsIgnoreCase(fromCurrency)){
                toCurrency = isoCurrencies.get(randomGenerator.nextInt(isoCurrencies.size())).getCurrencyCode();
            }
            Date date = new Date(Math.abs(System.currentTimeMillis() - randomGenerator.nextInt()));
            Double amount = randomGenerator.nextDouble() * 1000;
            String data = dealId + "," + fromCurrency + "," + toCurrency + "," + dateFormat.format(date) + "," + amount + "\n";
            writer.write(data);
        }
        writer.close();
    }
}
