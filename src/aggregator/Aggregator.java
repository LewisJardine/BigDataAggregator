package aggregator;


import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.csv.*;

public class Aggregator {

	private Map<String,Double> aggregation;
	private Map<String,Map<String,Double>> exchange;
	
	public Aggregator(Reader exchangeRates) {
		exchange = createRatesMap(exchangeRates);
	}
	
	public Map<String, Double> Aggregate(Reader reader, String baseCurrency){
		aggregation = new HashMap<String, Double>();
		try {
			Map<String,Double> rates = exchange.get(baseCurrency);
			System.out.println("Base currency = "+baseCurrency+" rates = "+rates);
			CSVParser parser = new CSVParser(reader, CSVFormat.RFC4180);
			try {
				for (CSVRecord record : parser) {
					String partner = record.get(0);
					String currency = record.get(1);
					double amount = Double.valueOf(record.get(2));
					double rate = 1.0;
					if (!currency.equalsIgnoreCase(baseCurrency)) {
						rate = rates.get(currency);
					}
					double total = 0.0;
					if (partner != null && aggregation.get(partner) != null) {
						total = aggregation.get(partner);
					}
					total += amount*rate;
					aggregation.put(partner, total);
				}
			} finally {
				parser.close();
				reader.close();
			}
		} catch (IOException ex) {
			return null;
		}
    	
        return aggregation;
	}
	
	
	private Map<String,Map<String,Double>> createRatesMap(Reader reader) {

		Map<String,Map<String,Double>> rates = new HashMap<String,Map<String,Double>>();
		try {
			CSVParser parser = new CSVParser(reader, CSVFormat.RFC4180);
			try {
				for (CSVRecord record : parser) {
					String currency1 = record.get(0);
					String currency2 = record.get(1);
					Double amount = Double.valueOf(record.get(2));
		            Map<String,Double> map = rates.get(currency1);
					if (map == null) {
						map = new HashMap<String, Double>();
						rates.put(currency1, map);
					}
		            map.put(currency2, amount);
		            map = null;
				}
			} finally {
				parser.close();
				reader.close();
			}
		} catch (IOException ex) {
			return null;
		}
		return rates;
	}
	
	public Map<String,Map<String,Double>> getRatesMap() {
		return exchange;
	}
}
