package au.gov.naa.digipres.spyd.core;

import java.io.IOException;
import java.sql.SQLException;

import au.gov.naa.digipres.spyd.dao.ConnectionException;

public class SpydMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Spyd spyd = new Spyd();
		try {
			SpydPreferences prefs = new SpydPreferences();
			prefs.updateFromFile("etc/spyd.properties");

			spyd.setPreferences(prefs);
			spyd.connectToDataStore(prefs.getDBConnectionProperties());

			spyd.startNetworkService();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
