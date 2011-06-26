package demo4;

import java.util.List;

import org.orman.dbms.Database;
import org.orman.dbms.mysql.MySQL;
import org.orman.dbms.mysql.MySQLSettingsImpl;
import org.orman.mapper.BulkInsert;
import org.orman.mapper.IdGenerationPolicy;
import org.orman.mapper.MappingSession;
import org.orman.mapper.Model;
import org.orman.mapper.ModelQuery;
import org.orman.mapper.SchemaCreationPolicy;
import org.orman.util.logging.ILogger;
import org.orman.util.logging.Log;
import org.orman.util.logging.Log4jAdapter;
import org.orman.util.logging.LoggingLevel;

public class BulkDemo {
	public static void main(String[] args) {
		int totalRecord;
		
		ILogger log = new Log4jAdapter();
		Log.setLogger(log);
		Log.setLevel(LoggingLevel.TRACE);
		
		MySQLSettingsImpl settings = new MySQLSettingsImpl("root", "root", "test");
		Database db = new MySQL(settings);
		
		MappingSession.registerDatabase(db);
		
		MappingSession.getConfiguration().setCreationPolicy(
				SchemaCreationPolicy.CREATE);
		MappingSession.getConfiguration().setIdGenerationPolicy(
				IdGenerationPolicy.MANUAL);
		
		MappingSession.start();
		
		//Seperated row bulk insert 
		totalRecord = Model.bulkInsert(Product.class, "./src/demo4/products.txt", "**",BulkInsert.NEW_LINE);
		
		//regular expression based bulk insert
		totalRecord = Model.bulkInsert(AnotherProduct.class, "./src/demo4/products2.txt", "<item name=\"(\\S+)\" pieces=\"(\\S+)\" price=\"(\\S+)\" />");
		
		if (totalRecord == -1) 
			System.out.println("Ops. Something went wrong!");
		else
			System.out.println(String.format("Total %d record(s) affected",totalRecord));
		

		List<Product> products = Model.fetchQuery(
				ModelQuery.select().from(Product.class).orderBy("-Product.id")
						.getQuery(), Product.class);
		
		System.out.println(products);
		
		db.closeConnection();
	}

}
