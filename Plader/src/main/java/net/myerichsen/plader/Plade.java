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
	@Override
	public String toString() {
		return "Plade [forlag=" + forlag + ", nummer=" + nummer + ", kunstner=" + kunstner + ", titel=" + titel
				+ ", volume=" + volume + ", medium=" + medium + ", antal=" + antal + ", aar=" + aar + ", oprettet="
				+ oprettet + "]";
	}

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

	public TableItem addItem(Table table) {
		TableItem item = new TableItem(table, SWT.NONE);
		String[] sa = new String[] { forlag, nummer, kunstner, titel, String.valueOf(volume), medium,
				String.valueOf(antal), String.valueOf(aar), oprettet };
		item.setText(sa);
		return item;
	}

	public String getForlag() {
		return forlag;
	}

	public void setForlag(String forlag) {
		this.forlag = forlag;
	}

	public String getNummer() {
		return nummer;
	}

	public void setNummer(String nummer) {
		this.nummer = nummer;
	}

	public String getKunstner() {
		return kunstner;
	}

	public void setKunstner(String kunstner) {
		this.kunstner = kunstner;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public String getMedium() {
		return medium;
	}

	public void setMedium(String medium) {
		this.medium = medium;
	}

	public int getAntal() {
		return antal;
	}

	public void setAntal(int antal) {
		this.antal = antal;
	}

	public int getAar() {
		return aar;
	}

	public void setAar(int aar) {
		this.aar = aar;
	}

	public String getOprettet() {
		return oprettet;
	}

	public void setOprettet(String oprettet) {
		this.oprettet = oprettet;
	}

	private String forlag;
	private String nummer;
	private String kunstner;
	private String titel;
	private int volume;
	private String medium;
	private int antal;
	private int aar;
	private String oprettet;
}
