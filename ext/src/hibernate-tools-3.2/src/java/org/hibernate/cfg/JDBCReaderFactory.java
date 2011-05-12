package org.hibernate.cfg;

import java.util.Properties;

import org.hibernate.cfg.reveng.JDBCReader;
import org.hibernate.cfg.reveng.ReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.dialect.H2MetaDataDialect;
import org.hibernate.cfg.reveng.dialect.JDBCMetaDataDialect;
import org.hibernate.cfg.reveng.dialect.MetaDataDialect;
import org.hibernate.cfg.reveng.dialect.MySQLMetaDataDialect;
import org.hibernate.cfg.reveng.dialect.OracleMetaDataDialect;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.MySQLDialect;
import org.hibernate.dialect.Oracle9Dialect;
import org.hibernate.util.ReflectHelper;

final public class JDBCReaderFactory {

	
	public static JDBCReader newJDBCReader(Properties cfg, Settings settings,
			ReverseEngineeringStrategy revengStrategy) {

		MetaDataDialect mdd = newMetaDataDialect( settings.getDialect(), cfg );

		return newJDBCReader( settings, revengStrategy, mdd );
	}

	public static JDBCReader newJDBCReader(Settings settings, ReverseEngineeringStrategy revengStrategy, MetaDataDialect mdd) {	
		return new JDBCReader( mdd, settings.getConnectionProvider(), settings
				.getSQLExceptionConverter(), settings.getDefaultCatalogName(), settings.getDefaultSchemaName(), revengStrategy );
	}

	public static MetaDataDialect newMetaDataDialect(Dialect dialect, Properties cfg) {		
		String property = cfg.getProperty( "hibernatetool.metadatadialect" );
		if ( property != null ) {
			try {
				return (MetaDataDialect) ReflectHelper.classForName( property,
						JDBCReaderFactory.class ).newInstance();
			}
			catch (Exception e) {
				throw new JDBCBinderException(
						"Could not load MetaDataDialect: " + property, e );
			}
		}
		if(dialect!=null) { // temporary hack to enable dialect specific metadata dialects. 
			if(dialect instanceof Oracle9Dialect) {
				return new OracleMetaDataDialect();
			} else if (dialect instanceof H2Dialect) {
				return new H2MetaDataDialect();
			} else if (dialect instanceof MySQLDialect) {
				return new MySQLMetaDataDialect();
			}
		}
		return new JDBCMetaDataDialect();
	}

}
