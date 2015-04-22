

import java.io.*;
import java.util.*;
import org.junit.*;

public class AggregatorTest {

	private Aggregator aggregator;
	
	@Before
	public void setUp() throws Exception {
		try {
			Reader rates = new FileReader("exchangerates.csv");
			try {
				aggregator = new Aggregator(rates);
			}
			finally {
				rates.close();
			}
	
		}
		catch (IOException e) {
			System.out.println("Exception during execution"+e);
		}
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {		
		String baseCurrency = "CHF";
		try {
			// 1 million line file (26MB)
			Reader reader = new FileReader("transactions.csv");
			Writer writer = new FileWriter("aggregation.csv");
			try {
			    
				Map<String, Double> aggregation = aggregator.Aggregate(reader, baseCurrency);
				Assert.assertNotNull(aggregation);
				for (Iterator<String> iter = aggregation.keySet().iterator(); iter.hasNext(); ){
					String key = iter.next();
					Double value = aggregation.get(key);
					writer.append(key+", "+value+"\n");
				}
				System.out.println("Total for Unlimited ltd. = "+aggregation.get("Unlimited ltd."));
				System.out.println("Total for Local plumber ltd. = "+aggregation.get("Local plumber ltd."));
				Assert.assertEquals(101047054d, aggregation.get("Unlimited ltd."), 1.0d);
				Assert.assertEquals(1.1452678E12d, aggregation.get("Local plumber ltd."), 0.001E10d);
			}
			finally {
				writer.close();
				reader.close();
			}
	
		}
		catch (IOException e) {
			System.out.println("Exception during execution"+e);
			Assert.fail("Exception thrown, "+e);
		}
	}

	@Test
	public void GBPTest() {		
		String baseCurrency = "GBP";
		try {
			Reader reader = new FileReader("GBPTrans.csv");
			Writer writer = new FileWriter("GBPaggregation.csv");
			try {
			    
				Map<String, Double> aggregation = aggregator.Aggregate(reader, baseCurrency);
				Assert.assertNotNull(aggregation);
				for (Iterator<String> iter = aggregation.keySet().iterator(); iter.hasNext(); ){
					String key = iter.next();
					Double value = aggregation.get(key);
					writer.append(key+", "+value+"\n");
				}
				Assert.assertEquals(9000d, aggregation.get("Unlimited ltd."), 1.0d);
				Assert.assertNull(aggregation.get("Local plumber ltd."));
			}
			finally {
				writer.close();
				reader.close();
			}
	
		}
		catch (IOException e) {
			System.out.println("Exception during execution"+e);
			Assert.fail("Exception thrown, "+e);
		}
	}

	@Test
	public void HUFTest() {		
		String baseCurrency = "HUF";
		try {
			Reader reader = new FileReader("GBPTrans.csv");
			Writer writer = new FileWriter("HUFaggregation.csv");
			try {
			    
				Map<String, Double> aggregation = aggregator.Aggregate(reader, baseCurrency);
				Assert.assertNotNull(aggregation);
				for (Iterator<String> iter = aggregation.keySet().iterator(); iter.hasNext(); ){
					String key = iter.next();
					Double value = aggregation.get(key);
					writer.append(key+", "+value+"\n");
				}
				Assert.assertEquals(21.59d, aggregation.get("Unlimited ltd."), 0.01d);
			}
			finally {
				writer.close();
				reader.close();
			}
	
		}
		catch (IOException e) {
			System.out.println("Exception during execution"+e);
			Assert.fail("Exception thrown, "+e);
		}
	}

	@Test
	public void createRatesMapTest() {
		System.out.println("Rates Map Test");
		Map<String, Map<String, Double>> map = aggregator.getRatesMap();
		Assert.assertNotNull(map);
		Assert.assertEquals(0.7019d,map.get("CHF").get("GBP"),0.0001);
		Assert.assertEquals(1.42d,map.get("GBP").get("CHF"),0.005);
		Assert.assertEquals(0.0034d,map.get("HUF").get("CHF"),0.0001);
	}
	
}
