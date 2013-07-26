package com.ovgu.mmorpg.cassandra.dao;

import java.util.Arrays;

import me.prettyprint.cassandra.model.AllOneConsistencyLevelPolicy;
import me.prettyprint.cassandra.model.BasicColumnDefinition;
import me.prettyprint.cassandra.model.BasicColumnFamilyDefinition;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.ThriftCfDef;
import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;
import me.prettyprint.cassandra.service.template.ThriftColumnFamilyTemplate;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.ComparatorType;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.MutationResult;
import me.prettyprint.hector.api.mutation.Mutator;

import org.apache.log4j.Logger;

import com.ovgu.mmorpg.cassandra.connection.CassandraHelper;
import com.ovgu.mmorpg.cassandra.factory.ItemFactory;
import com.ovgu.mmorpg.gamestate.Item;

public class InventoryDao {

	private static CassandraHelper dbHelper = CassandraHelper
			.getCassandraHelperInstance();

	private final static Logger logger = Logger.getLogger(InventoryDao.class
			.getName());

	private Cluster gameCluster = dbHelper.getGameCluster();

	private String KeySpaceName = dbHelper.getKeySpaceName();

	private static StringSerializer stringSerializer = StringSerializer.get();

	private Keyspace keyspaceOperator = HFactory.createKeyspace(KeySpaceName,
			gameCluster);

	// only create login, player not include
	private final String ColumnFamilyName = "Inventory";

	public MutationResult addItem(Item item) {
		ColumnFamilyTemplate<String, String> columnFamilyTemplate = new ThriftColumnFamilyTemplate<String, String>(
				keyspaceOperator, ColumnFamilyName, stringSerializer,
				stringSerializer);
		keyspaceOperator
				.setConsistencyLevelPolicy(new AllOneConsistencyLevelPolicy());
		Mutator<String> mutator = columnFamilyTemplate.createMutator();
		// use the playeruuid as inventory's rowkey
		String name = item.getStringUUID();
		// make other properties of item as a string
		String value = item.storeValue();

		MutationResult mr = mutator.insert(item.getOwnerUUID(),
				ColumnFamilyName, HFactory.createColumn(name, value));
		return mr;
	}

	public void createInventorySchema() {
		logger.info("======>creating cassandra schema Inventory ");

		// the item uuidString
		BasicColumnDefinition colItemUUIDString = new BasicColumnDefinition();
		colItemUUIDString.setName(StringSerializer.get().toByteBuffer(
				"itemuuid"));
		colItemUUIDString.setValidationClass(ComparatorType.BYTESTYPE
				.getClassName());

		// the money of the users
		BasicColumnDefinition colMoney = new BasicColumnDefinition();
		colMoney.setName(StringSerializer.get().toByteBuffer("money"));
		colMoney.setValidationClass(ComparatorType.BYTESTYPE.getClassName());
		// the last accssed ip

		// definite a basic cf, add all columns in it.
		BasicColumnFamilyDefinition basicItemDefinition = new BasicColumnFamilyDefinition();
		basicItemDefinition.setKeyspaceName(KeySpaceName); // keyspace name
		basicItemDefinition.setName(ColumnFamilyName);// column family name
		// use utf8 type, use command list login, human readable column name
		basicItemDefinition.setComparatorType(ComparatorType.UTF8TYPE);
		basicItemDefinition.addColumnDefinition(colItemUUIDString);
		basicItemDefinition.addColumnDefinition(colMoney);

		ColumnFamilyDefinition cfItemDef = new ThriftCfDef(basicItemDefinition);
		KeyspaceDefinition keyspaceDefinition = HFactory
				.createKeyspaceDefinition(KeySpaceName,
						"org.apache.cassandra.locator.SimpleStrategy", 1,
						Arrays.asList(cfItemDef));

		try {

			if (gameCluster.describeKeyspace(KeySpaceName) != null) {
				try {
					// if the keyspace exists, drop the columnfamily, with the
					// same name
					gameCluster.dropColumnFamily(KeySpaceName,
							ColumnFamilyName, true);
				} catch (HectorException he) {

				} finally {
					// add columnfamily to the exists keyspace
					gameCluster.addColumnFamily(cfItemDef);
				}
			} else {
				logger.debug("Keyspace " + KeySpaceName
						+ " not exists, create it");
				gameCluster.addKeyspace(keyspaceDefinition);
			}

		} catch (HectorException he) {
			logger.warn("a error occured :" + he.toString());

		} finally {

		}

		logger.info(" cassandra Item schema sucessfuly <======");

	}

	public static void main(String[] args) {
		InventoryDao id = new InventoryDao();
		// id.createInventorySchema();
		for (int i = 0; i < 10; i++) {
			id.addItem(ItemFactory.createItem());
		}
	}

}
