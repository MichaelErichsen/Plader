package net.myerichsen.plader;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

/**
 * Data carrier
 *
 * @author Michael Erichsen
 */
public class Plade {
	private int antal;

	private String forlag;

	private String kunstner;

	private String medium;

	private String nummer;

	private String oprettet;

	private String titel;

	private int volume;

	private int aar;

	/**
	 * @param forlag
	 * @param nummer
	 * @param kunstner
	 * @param titel
	 * @param volume
	 * @param medium
	 * @param antal
	 * @param aar
	 * @param oprettet
	 */
	public Plade(String forlag, String nummer, String kunstner, String titel, int volume, String medium, int antal,
			int aar, String oprettet) {
		super();
		this.forlag = forlag;
		this.nummer = nummer;
		this.kunstner = kunstner;
		this.titel = titel;
		this.volume = volume;
		this.medium = medium;
		this.antal = antal;
		this.aar = aar;
		this.oprettet = oprettet;
	}

	/**
	 * @param table
	 * @return item
	 */
	public TableItem addItem(Table table) {
		final var item = new TableItem(table, SWT.NONE);
		final var sa = new String[] { forlag, nummer, kunstner, titel, String.valueOf(volume), medium,
				String.valueOf(antal), String.valueOf(aar), oprettet };
		item.setText(sa);
		return item;
	}

	/**
	 * @return antal
	 */
	public int getAntal() {
		return antal;
	}

	/**
	 * @return forlag
	 */
	public String getForlag() {
		return forlag;
	}

	/**
	 * @return kunstner
	 */
	public String getKunstner() {
		return kunstner;
	}

	/**
	 * @return medium
	 */
	public String getMedium() {
		return medium;
	}

	/**
	 * @return nummer
	 */
	public String getNummer() {
		return nummer;
	}

	/**
	 * @return oprettet
	 */
	public String getOprettet() {
		return oprettet;
	}

	/**
	 * @return titel
	 */
	public String getTitel() {
		return titel;
	}

	/**
	 * @return volume
	 */
	public int getVolume() {
		return volume;
	}

	/**
	 * @return aar
	 */
	public int getAar() {
		return aar;
	}

	/**
	 * @param antal
	 */
	public void setAntal(int antal) {
		this.antal = antal;
	}

	/**
	 * @param forlag
	 */
	public void setForlag(String forlag) {
		this.forlag = forlag;
	}

	/**
	 * @param kunstner
	 */
	public void setKunstner(String kunstner) {
		this.kunstner = kunstner;
	}

	/**
	 * @param medium
	 */
	public void setMedium(String medium) {
		this.medium = medium;
	}

	/**
	 * @param nummer
	 */
	public void setNummer(String nummer) {
		this.nummer = nummer;
	}

	/**
	 * @param oprettet
	 */
	public void setOprettet(String oprettet) {
		this.oprettet = oprettet;
	}

	/**
	 * @param titel
	 */
	public void setTitel(String titel) {
		this.titel = titel;
	}

	/**
	 * @param volume
	 */
	public void setVolume(int volume) {
		this.volume = volume;
	}

	/**
	 * @param aar
	 */
	public void setAar(int aar) {
		this.aar = aar;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Plade [antal=");
		builder.append(antal);
		builder.append(", forlag=");
		builder.append(forlag);
		builder.append(", kunstner=");
		builder.append(kunstner);
		builder.append(", medium=");
		builder.append(medium);
		builder.append(", nummer=");
		builder.append(nummer);
		builder.append(", oprettet=");
		builder.append(oprettet);
		builder.append(", titel=");
		builder.append(titel);
		builder.append(", volume=");
		builder.append(volume);
		builder.append(", aar=");
		builder.append(aar);
		builder.append("]");
		return builder.toString();
	}
}
