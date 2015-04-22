import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;


public class BigDataAggregatorTest {

	public static void main(String[] args) {
		String baseCurrency = "CHF";
		try {
			final Reader rates = new FileReader("exchangerates.csv");
			final Reader reader = new FileReader("transactions.csv");
			final Writer writer = new FileWriter("aggregation.csv");
			try {
			    
				Aggregator aggregator = new Aggregator(rates);
				Map<String, Double> aggregation = aggregator.Aggregate(reader, baseCurrency);
				for (Iterator<String> iter = aggregation.keySet().iterator(); iter.hasNext(); ){
					String key = iter.next();
					Double value = aggregation.get(key);
					writer.append(key+", "+value+"\n");
				}
				System.out.println("Total for Unlimited ltd. = "+aggregation.get("Unlimited ltd."));
			}
			finally {
				writer.close();
				rates.close();
				reader.close();
			}

		}
		catch (IOException e) {
			System.out.println("Exception during execution"+e);
		}
	}


}
