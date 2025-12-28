package net.myerichsen.plader.batch;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Change old timestamps to new date format
 *
 * @author Michael Erichsen
 */
public class RetOprettetDato {

	/**
	 * Main method
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			final var connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres",
					"admin");
			final var psL = connection.prepareStatement("SELECT DISTINCT OPRETTET FROM PLADE");
			final var psU = connection.prepareStatement("UPDATE PLADE SET OPRETTET = ? WHERE OPRETTET = ?");
			final List<String> liste = new ArrayList<>();
			var rettet = "";
			final var pattern = Pattern.compile("(\\d{2})-(\\d{2})-(\\d{4})\\s\\d{2}:\\d{2}");
			Matcher matcher = null;
			final var rs = psL.executeQuery();

			while (rs.next()) {
				liste.add(rs.getString("OPRETTET"));
			}

			for (final String string : liste) {
				matcher = pattern.matcher(string);

				if (matcher.find()) {
					rettet = matcher.group(3) + "-" + matcher.group(2) + "-" + matcher.group(1);
					psU.setString(1, rettet);
					psU.setString(2, string);

					final var updateCount = psU.executeUpdate();

					if (updateCount > 0) {
						System.out.println(string + " rettet til " + rettet);
					}
				}
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		}
	}

}
