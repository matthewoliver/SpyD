package au.gov.naa.digipres.spyd.core;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import au.gov.naa.digipres.spyd.dao.ConnectionException;

public class SpydMain {

	public static final String DEFAULT_PROPERTIES_PATH = "etc/spyd.properties";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Spyd spyd = new Spyd();
		try {
			SpydPreferences prefs = new SpydPreferences();
			Options options = constructOptions();
			BasicParser parser = new BasicParser();
			CommandLine commandLine = parser.parse(options, args);

			if (commandLine.hasOption('h')) {
				HelpFormatter helpFormatter = new HelpFormatter();
				helpFormatter.printHelp("spyd [--config <arg>]", options);
				System.exit(1);
			}

			String pathToConfig = DEFAULT_PROPERTIES_PATH;
			if (commandLine.hasOption('c')) {
				pathToConfig = commandLine.getOptionValue('c');
			}
			prefs.updateFromFile(pathToConfig);

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
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Constructs command-line options
	 */
	private static Options constructOptions() {
		Options options = new Options();

		Option option = new Option("c", "config", true, "Config/Properties File");
		options.addOption(option);

		options.addOption("h", "help", false, "Print usage information");

		return options;
	}
}
