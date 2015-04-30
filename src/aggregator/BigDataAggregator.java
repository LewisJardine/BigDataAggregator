package aggregator;

import java.io.*;
import java.util.Iterator;
import java.util.Map;

public class BigDataAggregator {

	public static void main(String[] args) {
		String baseCurrency = args[3];
		try {
			final Reader reader = new FileReader(args[0]);
			final Reader rates = new FileReader(args[1]);
			final Writer writer = new FileWriter("aggregation.csv");
			try {
			    long time = System.currentTimeMillis();
				Aggregator aggregator = new Aggregator(rates);
				Map<String, Double> aggregation = aggregator.Aggregate(reader, baseCurrency);
				// Write aggregation file
				for (Iterator<String> iter = aggregation.keySet().iterator(); iter.hasNext(); ){
					String key = iter.next();
					Double value = aggregation.get(key);
					writer.append(key+", "+value+"\n");
				}
				// Output task 2
				System.out.println("Total for "+args[2]+" = "+aggregation.get(args[2]));
				System.out.println("Time taken = "+(System.currentTimeMillis()-time)+"ms");
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
