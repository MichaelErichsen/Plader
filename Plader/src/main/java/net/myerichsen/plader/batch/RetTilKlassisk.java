package net.myerichsen.plader.batch;

import static net.myerichsen.plader.Konstanter.GODADDY_URL;
import static net.myerichsen.plader.Konstanter.PASSWORD;
import static net.myerichsen.plader.Konstanter.USERID;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Change flag for klassisk
 *
 * @author Michael Erichsen, 2026
 */
public class RetTilKlassisk {

	/**
	 * Main method
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		String[] strenge = { "Albinoni", "Beethoven", "Bizet", "Borodin", "Brahms", "Debussy", "Delibes", "Donizetti",
				"Dvorak", "Elgar", "Faur", "Gade", "Gershwin", "Grieg", "Holst", "I Musici", "Janácek", "Khat",
				"Kunzen", "Langg", "Mahler", "Mendelsohn", "Mozart", "Nielsen", "Orff", "Porgy", "Proko", "Puccini",
				"Ravel", "Rimsk", "Satie", "Shosta", "Sibelius", "Smetana", "Strauss", "Tjai", "Verdi", "Vivaldi"

		};

		Properties props = new Properties();
		props.setProperty("user", USERID);
		props.setProperty("password", PASSWORD);
		props.setProperty("useSSL", "true");

		try {
			Connection connection = DriverManager.getConnection(GODADDY_URL, props);
			final var psU = connection.prepareStatement("UPDATE PLADE SET KLASSISK = 'JA' WHERE KUNSTNER LIKE ?");

			for (int i = 0; i < strenge.length; i++) {

				psU.setString(1, strenge[i] + "%");

				final var updateCount = psU.executeUpdate();
				

				if (updateCount > 0) {
					System.out.println(strenge[i] + " rettet til JA");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
