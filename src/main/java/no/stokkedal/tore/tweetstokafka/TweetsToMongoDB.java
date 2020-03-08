/**
 * 
 */
package no.stokkedal.tore.tweetstokafka;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Class that add Tweets to MongoDB, expected used by TweetsFromKafkaToMongo.
 * 
 * @author tore.stokkedal@ibm.com
 *
 */
public class TweetsToMongoDB {

	private Logger log = LoggerFactory.getLogger(this.getClass().getName());

	String mongoHost = "rhel1.local";
	String databaseName = "thedeveloper";
	String collectionName = "tweets";

//	String mongoHost = "192.168.39.249";
//	String database = "thedeveloper";
//	String collectionName = "persons";

	MongoClient mongoClient = null;
	MongoDatabase dbDatabase = null;
	MongoCollection<Document> collection = null;

	/**
	 * Iniltialise on localhost, standard port 27017
	 */
	public TweetsToMongoDB() {

	}

	/*
	 * Add record
	 */
	public void addRecord(String record) throws Exception {
		log.info("Entering add record");
		try {
			connectParameters();
			Document doc = new Document(Document.parse(record));
			collection.insertOne(doc);
			log.info("Inserted tweet to Mongo" + doc.toString());
		} catch (Exception e) {
			log.error("Error insering record: " + e.getMessage());
			e.printStackTrace();
		}
		log.info("Leaving add record");
	}

	/**
	 * Connect with default / configed parameters
	 */
	public void connectParameters() {
		try {
			mongoClient = new MongoClient(mongoHost, 27017);
			dbDatabase = mongoClient.getDatabase(databaseName);
			collection = dbDatabase.getCollection(collectionName);

			log.info("Set host, connection and collection. " + toString());

		} catch (Exception e) {
			log.info("Error when opening db" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Just to check availability of MongoDB at startup
	 */
	public void isMongoAvailable() throws MongoException {
		connectParameters();
		collection.count();
	}

	@Override
	protected void finalize() throws Throwable {
		mongoClient.close();
	}

	/**
	 * Return state of the class
	 */
	public String toString() {
		return "MongoHost; " + mongoHost + "Database; " + databaseName + " Collection " + collectionName;
				// + " MongoClient" + mongoClient.toString(); May give null pointer ....

	}
}
