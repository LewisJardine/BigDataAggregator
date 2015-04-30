package aggregator;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CreateTransFile {

	public static void main(String[] args) {
		List<String> currency;
		List<String> partners;
		try {
			Reader rates = new FileReader("exchangerates.csv");
			Reader reader = new FileReader("transactions.csv");
			final Writer writer = new FileWriter("trans100m.csv");
			try {
				Aggregator aggregator = new Aggregator(rates);
				currency = new ArrayList<String>(aggregator.getRatesMap().keySet());
				Map<String, Double> aggregation = aggregator.Aggregate(reader, "GBP");
				partners = new ArrayList<String>(aggregation.keySet());
                Random rng = new Random(56123467l);
				int max = Integer.valueOf(args[0]);
				System.out.println("Max = "+max);
				for (int i = 0; i < max; i++) {
					int part = (int) (rng.nextFloat()*partners.size());
					int cur  = (int) (rng.nextFloat()*currency.size());
					writer.append(partners.get(part)+","+currency.get(cur)+","+((int) (100000*rng.nextFloat()))/100.0+"\n");
				}

			}
			finally {
				rates.close();
				reader.close();
				writer.close();
				System.out.println("Done");
			}
	
		}
		catch (IOException e) {
			System.out.println("Exception during reading"+e);
		}
		
	}

}
