package net.myerichsen.plader.batch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
			Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres",
					"admin");
			PreparedStatement psL = connection.prepareStatement("SELECT DISTINCT OPRETTET FROM PLADE");
			PreparedStatement psU = connection.prepareStatement("UPDATE PLADE SET OPRETTET = ? WHERE OPRETTET = ?");
			List<String> liste = new ArrayList<>();
			String rettet = "";
			Pattern pattern = Pattern.compile("(\\d{2})-(\\d{2})-(\\d{4})\\s\\d{2}:\\d{2}");
			Matcher matcher = null;
			ResultSet rs = psL.executeQuery();

			while (rs.next()) {
				liste.add(rs.getString("OPRETTET"));
			}

			for (String string : liste) {
				matcher = pattern.matcher(string);

				if (matcher.find()) {
					rettet = matcher.group(3) + "-" + matcher.group(2) + "-" + matcher.group(1);
					psU.setString(1, rettet);
					psU.setString(2, string);

					int updateCount = psU.executeUpdate();

					if (updateCount > 0) {
						System.out.println(string + " rettet til " + rettet);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
