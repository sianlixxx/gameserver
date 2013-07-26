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
import me.prettyprint.hector.api.ddl.ColumnIndexType;
import me.prettyprint.hector.api.ddl.ComparatorType;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.MutationResult;
import me.prettyprint.hector.api.mutation.Mutator;

import org.apache.log4j.Logger;

import com.ovgu.mmorpg.cassandra.connection.CassandraHelper;
import com.ovgu.mmorpg.cassandra.factory.GamePlayerFactory;
import com.ovgu.mmorpg.gamestate.GamePlayer;

public class GamePlayerDao {

	private static CassandraHelper dbHelper = CassandraHelper
			.getCassandraHelperInstance();

	private final static Logger logger = Logger.getLogger(GamePlayerDao.class
			.getName());

	private final static Cluster gameCluster = dbHelper.getGameCluster();

	private final static String KeySpaceName = dbHelper.getKeySpaceName();

	private final static StringSerializer stringSerializer = StringSerializer
			.get();

	private final static Keyspace keyspaceOperator = HFactory.createKeyspace(
			KeySpaceName, gameCluster);

	private final static String ColumnFamilyName = "GamePlayer";

	public MutationResult addPlayer(GamePlayer gp) {
		ColumnFamilyTemplate<String, String> columnFamilyTemplate = new ThriftColumnFamilyTemplate<String, String>(
				keyspaceOperator, ColumnFamilyName, stringSerializer,
				stringSerializer);
		keyspaceOperator
				.setConsistencyLevelPolicy(new AllOneConsistencyLevelPolicy());
		Mutator<String> mutator = columnFamilyTemplate.createMutator();

		GamePlayerDao
				.addInsseration(mutator, gp.getUUIDString(), gp.getUserName(),
						gp.getUserPassword(), gp.getRegistDate(),
						gp.getLastActiveIp(), gp.getLastActiceDate(),
						gp.getTrueName(), gp.getEmail(), gp.getBirth(),
						String.valueOf(gp.getMapId()), gp.getHeroClass(),
						gp.getHeroRace(), String.valueOf(gp.getCurrHp()),
						String.valueOf(gp.getMaxHp()),
						String.valueOf(gp.getCurrExp()),
						String.valueOf(gp.getMaxExp()),
						String.valueOf(gp.getStrength()),
						String.valueOf(gp.getAttack()),
						String.valueOf(gp.getDefense()),
						String.valueOf(gp.getClassId()),
						String.valueOf(gp.getRaceId()));
		MutationResult mr = mutator.execute();
		return mr;
	}

	private static void addInsseration(Mutator<String> mut, String rowkey,
			String username, String userpassword, String registdate,
			String lastactiveip, String lastactivedate, String truename,
			String email, String birthday, String mapid, String heroclass,
			String herorace, String currhp, String maxhp, String currexp,
			String maxexp, String strength, String attack, String defense,
			String classid, String raceid) {

		mut.addInsertion(rowkey, ColumnFamilyName,
				HFactory.createColumn("username", username));

		mut.addInsertion(rowkey, ColumnFamilyName,
				HFactory.createColumn("userpassword", userpassword));

		mut.addInsertion(rowkey, ColumnFamilyName,
				HFactory.createColumn("registdate", registdate));

		mut.addInsertion(rowkey, ColumnFamilyName,
				HFactory.createColumn("lastactiveip", lastactiveip));

		mut.addInsertion(rowkey, ColumnFamilyName,
				HFactory.createColumn("lastactivedate", lastactivedate));

		mut.addInsertion(rowkey, ColumnFamilyName,
				HFactory.createColumn("truename", truename));

		mut.addInsertion(rowkey, ColumnFamilyName,
				HFactory.createColumn("email", email));

		mut.addInsertion(rowkey, ColumnFamilyName,
				HFactory.createColumn("birthday", birthday));

		mut.addInsertion(rowkey, ColumnFamilyName,
				HFactory.createColumn("mapid", mapid));

		mut.addInsertion(rowkey, ColumnFamilyName,
				HFactory.createColumn("heroclass", heroclass));

		mut.addInsertion(rowkey, ColumnFamilyName,
				HFactory.createColumn("herorace", herorace));

		mut.addInsertion(rowkey, ColumnFamilyName,
				HFactory.createColumn("currhp", currhp));

		mut.addInsertion(rowkey, ColumnFamilyName,
				HFactory.createColumn("maxhp", maxhp));

		mut.addInsertion(rowkey, ColumnFamilyName,
				HFactory.createColumn("currexp", currexp));

		mut.addInsertion(rowkey, ColumnFamilyName,
				HFactory.createColumn("maxexp", maxexp));

		mut.addInsertion(rowkey, ColumnFamilyName,
				HFactory.createColumn("strength", strength));

		mut.addInsertion(rowkey, ColumnFamilyName,
				HFactory.createColumn("attack", attack));

		mut.addInsertion(rowkey, ColumnFamilyName,
				HFactory.createColumn("defense", defense));

		mut.addInsertion(rowkey, ColumnFamilyName,
				HFactory.createColumn("classid", classid));

		mut.addInsertion(rowkey, ColumnFamilyName,
				HFactory.createColumn("raceid", raceid));
	}

	public static void createGamePlayerSchema() {
		logger.info("======>creating cassandra schema gameplayer ");

		// the user name of a account username column has a index, so we can use
		// Rangeslicesquery to retrieve it by setting addequalsexpression

		// in the sql database, this colums name is id!!!!!!!!
		// this column is the secondary index, we can retrive it use
		// "where username = xx"
		BasicColumnDefinition colUserNameDef = new BasicColumnDefinition();
		colUserNameDef.setName(stringSerializer.toByteBuffer("username"));
		colUserNameDef.setIndexName("loginname_idx");
		colUserNameDef.setIndexType(ColumnIndexType.KEYS);
		colUserNameDef.setValidationClass(ComparatorType.BYTESTYPE
				.getClassName());

		// the user password
		BasicColumnDefinition colUserPassword = new BasicColumnDefinition();
		colUserPassword.setName(stringSerializer.toByteBuffer("userpassword"));
		colUserPassword.setValidationClass(ComparatorType.BYTESTYPE
				.getClassName());

		// the register date of the account
		BasicColumnDefinition colDateRegister = new BasicColumnDefinition();
		colDateRegister.setName(stringSerializer.toByteBuffer("registdate"));
		colDateRegister.setValidationClass(ComparatorType.BYTESTYPE
				.getClassName());
		// the last accssed ip
		BasicColumnDefinition colLastIp = new BasicColumnDefinition();
		colLastIp.setName(stringSerializer.toByteBuffer("lastactiveip"));
		colLastIp.setValidationClass(ComparatorType.BYTESTYPE.getClassName());

		BasicColumnDefinition colLastActive = new BasicColumnDefinition();
		colLastActive.setName(stringSerializer.toByteBuffer("lastactivedate"));
		colLastActive.setValidationClass(ComparatorType.BYTESTYPE
				.getClassName());

		BasicColumnDefinition colTrueName = new BasicColumnDefinition();
		colTrueName.setName(stringSerializer.toByteBuffer("truename"));
		colTrueName.setValidationClass(ComparatorType.BYTESTYPE.getClassName());

		BasicColumnDefinition colEmail = new BasicColumnDefinition();
		colEmail.setName(stringSerializer.toByteBuffer("email"));
		colEmail.setValidationClass(ComparatorType.BYTESTYPE.getClassName());

		BasicColumnDefinition colBirth = new BasicColumnDefinition();
		colBirth.setName(stringSerializer.toByteBuffer("birthday"));
		colBirth.setValidationClass(ComparatorType.BYTESTYPE.getClassName());

		BasicColumnDefinition colMap = new BasicColumnDefinition();
		colMap.setName(stringSerializer.toByteBuffer("mapid"));
		colMap.setValidationClass(ComparatorType.BYTESTYPE.getClassName());

		BasicColumnDefinition colheroClass = new BasicColumnDefinition();
		colheroClass.setName(stringSerializer.toByteBuffer("heroclass"));
		colheroClass
				.setValidationClass(ComparatorType.BYTESTYPE.getClassName());

		BasicColumnDefinition colheroRace = new BasicColumnDefinition();
		colheroRace.setName(stringSerializer.toByteBuffer("herorace"));
		colheroRace.setValidationClass(ComparatorType.BYTESTYPE.getClassName());

		BasicColumnDefinition colCurrHp = new BasicColumnDefinition();
		colCurrHp.setName(stringSerializer.toByteBuffer("currhp"));
		colCurrHp.setValidationClass(ComparatorType.BYTESTYPE.getClassName());

		BasicColumnDefinition colMaxHp = new BasicColumnDefinition();
		colMaxHp.setName(stringSerializer.toByteBuffer("maxhp"));
		colMaxHp.setValidationClass(ComparatorType.BYTESTYPE.getClassName());

		BasicColumnDefinition colCurrExp = new BasicColumnDefinition();
		colCurrExp.setName(stringSerializer.toByteBuffer("currexp"));
		colCurrExp.setValidationClass(ComparatorType.BYTESTYPE.getClassName());

		BasicColumnDefinition colMaxExp = new BasicColumnDefinition();
		colMaxExp.setName(stringSerializer.toByteBuffer("maxexp"));
		colMaxExp.setValidationClass(ComparatorType.BYTESTYPE.getClassName());

		BasicColumnDefinition colStrength = new BasicColumnDefinition();
		colStrength.setName(stringSerializer.toByteBuffer("strength"));
		colStrength.setValidationClass(ComparatorType.BYTESTYPE.getClassName());

		BasicColumnDefinition colAttack = new BasicColumnDefinition();
		colAttack.setName(stringSerializer.toByteBuffer("attack"));
		colAttack.setValidationClass(ComparatorType.BYTESTYPE.getClassName());

		BasicColumnDefinition colDefense = new BasicColumnDefinition();
		colDefense.setName(stringSerializer.toByteBuffer("defense"));
		colDefense.setValidationClass(ComparatorType.BYTESTYPE.getClassName());

		BasicColumnDefinition colClassId = new BasicColumnDefinition();
		colClassId.setName(stringSerializer.toByteBuffer("classid"));
		colClassId.setValidationClass(ComparatorType.BYTESTYPE.getClassName());

		BasicColumnDefinition colRaceId = new BasicColumnDefinition();
		colRaceId.setName(stringSerializer.toByteBuffer("raceid"));
		colRaceId.setValidationClass(ComparatorType.BYTESTYPE.getClassName());

		// definite a basic cf, add all columns in it.
		BasicColumnFamilyDefinition basicCFGamePlayerDef = new BasicColumnFamilyDefinition();
		basicCFGamePlayerDef.setKeyspaceName(KeySpaceName); // keyspace name
		basicCFGamePlayerDef.setName(ColumnFamilyName);// column family name
		// use utf8 type, use command list login, human readable column name
		basicCFGamePlayerDef.setComparatorType(ComparatorType.UTF8TYPE);
		basicCFGamePlayerDef.addColumnDefinition(colUserNameDef);
		basicCFGamePlayerDef.addColumnDefinition(colUserPassword);
		basicCFGamePlayerDef.addColumnDefinition(colDateRegister);
		basicCFGamePlayerDef.addColumnDefinition(colLastIp);
		basicCFGamePlayerDef.addColumnDefinition(colLastActive);
		basicCFGamePlayerDef.addColumnDefinition(colTrueName);
		basicCFGamePlayerDef.addColumnDefinition(colEmail);
		basicCFGamePlayerDef.addColumnDefinition(colBirth);

		basicCFGamePlayerDef.addColumnDefinition(colMap);
		basicCFGamePlayerDef.addColumnDefinition(colheroClass);
		basicCFGamePlayerDef.addColumnDefinition(colheroRace);
		basicCFGamePlayerDef.addColumnDefinition(colCurrHp);
		basicCFGamePlayerDef.addColumnDefinition(colMaxHp);
		basicCFGamePlayerDef.addColumnDefinition(colCurrExp);
		basicCFGamePlayerDef.addColumnDefinition(colMaxExp);
		basicCFGamePlayerDef.addColumnDefinition(colStrength);
		basicCFGamePlayerDef.addColumnDefinition(colAttack);
		basicCFGamePlayerDef.addColumnDefinition(colDefense);

		basicCFGamePlayerDef.addColumnDefinition(colClassId);
		basicCFGamePlayerDef.addColumnDefinition(colRaceId);

		ColumnFamilyDefinition cfLoginUserDef = new ThriftCfDef(
				basicCFGamePlayerDef);
		KeyspaceDefinition keyspaceDefinition = HFactory
				.createKeyspaceDefinition(KeySpaceName,
						"org.apache.cassandra.locator.SimpleStrategy", 1,
						Arrays.asList(cfLoginUserDef));

		try {

			if (gameCluster.describeKeyspace(KeySpaceName) != null) {
				try {
					gameCluster.dropColumnFamily(KeySpaceName,
							ColumnFamilyName, true);
				} catch (HectorException he) {

				} finally {
					gameCluster.addColumnFamily(cfLoginUserDef);

				}
			} else {
				logger.debug("Keyspace " + KeySpaceName
						+ " not exists, create it");
				gameCluster.addKeyspace(keyspaceDefinition);
			}

		} catch (HectorException he) {
			logger.warn("a error occured :" + he.toString());
		}

		logger.info(" cassandra gameplayer schema sucessfuly <======");

	}

	public static void main(String[] args) {
		GamePlayerDao.createGamePlayerSchema();
		GamePlayerDao gpd = new GamePlayerDao();
//		for (int i = 0; i < 20; i++) {
			gpd.addPlayer(GamePlayerFactory.createPlayer());
//
//		}
	}

}
