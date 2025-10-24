package net.myerichsen.plader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.graphics.Point;

/**
 * Vedligehold en pladesamling
 *
 * @author Michael Erichsen
 */
public class Plader {
	private static Table tablePlader;
	private static TableItem item;
	private static Connection connection;
	private static PreparedStatement udenFilter;
	private static ResultSet rs;

	/**
	 * Launch the application.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = Display.getDefault();
		Shell shlErichsensPladesamling = new Shell();
		shlErichsensPladesamling.setMinimumSize(new Point(1200, 380));
		shlErichsensPladesamling.setSize(957, 684);
		shlErichsensPladesamling.setText("Erichsens pladesamling");
		shlErichsensPladesamling.setLayout(new GridLayout(1, false));

		Composite composite = new Composite(shlErichsensPladesamling, SWT.NONE);
		composite.setLayout(new RowLayout(SWT.HORIZONTAL));

		Button btnFiltrer = new Button(composite, SWT.NONE);
		btnFiltrer.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FilterDialog filterDialog = new FilterDialog(shlErichsensPladesamling, SWT.NONE);
				List<Plade> pladeListe = filterDialog.open(connection, tablePlader);
				tablePlader.removeAll();

				if (pladeListe != null) {
					for (Plade plade : pladeListe) {
						plade.addItem(tablePlader);
					}
				} else {
					populateFully(shlErichsensPladesamling);
				}

			}
		});
		btnFiltrer.setText("Filtrér");

		Button btnOpret = new Button(composite, SWT.NONE);
		btnOpret.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				OpretDialog opretDialog = new OpretDialog(shlErichsensPladesamling, SWT.NONE);
				Plade plade = opretDialog.open(connection);
				if (plade != null) {
					TableItem item2 = plade.addItem(tablePlader);
					tablePlader.showItem(item2);
				}
			}
		});
		btnOpret.setText("Opret");

		Label lblVlgEnRkke = new Label(composite, SWT.NONE);
		lblVlgEnRkke.setText("Vælg en række, før du retter eller sletter");

		Button btnRet = new Button(composite, SWT.NONE);
		btnRet.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem[] selection = tablePlader.getSelection();

				if (selection.length < 1) {
					MessageBox messageBox = new MessageBox(shlErichsensPladesamling, SWT.ICON_WARNING);
					messageBox.setMessage("Vælg først en række!");
					messageBox.open();
				}

				TableItem tableItem = selection[0];
				int i = tablePlader.getSelectionIndices()[0];
				OpdaterDialog opdaterDialog = new OpdaterDialog(shlErichsensPladesamling, SWT.NONE);
				Plade plade = opdaterDialog.open(connection, tableItem);

				if (plade != null) {
					tablePlader.remove(i);
					plade.addItem(tablePlader);
				}
			}
		});
		btnRet.setText("Ret");

		Button btnSlet = new Button(composite, SWT.NONE);
		btnSlet.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem[] selection = tablePlader.getSelection();

				if (selection.length < 1) {
					MessageBox messageBox = new MessageBox(shlErichsensPladesamling, SWT.ICON_WARNING);
					messageBox.setMessage("Vælg først en række!");
					messageBox.open();
				}

				TableItem tableItem = selection[0];
				int i = tablePlader.getSelectionIndices()[0];
				SletDialog sletDialog = new SletDialog(shlErichsensPladesamling, SWT.NONE);
				boolean slettet = sletDialog.open(connection, tableItem);
				if (slettet) {
					tablePlader.remove(i);
				}
			}
		});
		btnSlet.setText("Slet");

		tablePlader = new Table(shlErichsensPladesamling, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE);
		GridData gd_tablePlader = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_tablePlader.widthHint = 855;
		tablePlader.setLayoutData(gd_tablePlader);
		tablePlader.setHeaderVisible(true);
		tablePlader.setLinesVisible(true);

		TableColumn tblclmnForlag = new TableColumn(tablePlader, SWT.NONE);
		tblclmnForlag.setWidth(150);
		tblclmnForlag.setText("Forlag");

		TableColumn tblclmnNummer = new TableColumn(tablePlader, SWT.NONE);
		tblclmnNummer.setWidth(150);
		tblclmnNummer.setText("Nummer");

		TableColumn tblclmnKunstner = new TableColumn(tablePlader, SWT.NONE);
		tblclmnKunstner.setWidth(250);
		tblclmnKunstner.setText("Kunstner");

		TableColumn tblclmnTitel = new TableColumn(tablePlader, SWT.NONE);
		tblclmnTitel.setWidth(250);
		tblclmnTitel.setText("Titel");

		TableColumn tblclmnVolume = new TableColumn(tablePlader, SWT.NONE);
		tblclmnVolume.setWidth(50);
		tblclmnVolume.setText("Volume");

		TableColumn tblclmnMedium = new TableColumn(tablePlader, SWT.NONE);
		tblclmnMedium.setWidth(58);
		tblclmnMedium.setText("Medium");

		TableColumn tblclmnAntal = new TableColumn(tablePlader, SWT.NONE);
		tblclmnAntal.setWidth(42);
		tblclmnAntal.setText("Antal");

		TableColumn tblclmnAar = new TableColumn(tablePlader, SWT.NONE);
		tblclmnAar.setWidth(52);
		tblclmnAar.setText("År");

		TableColumn tblclmnOprettet = new TableColumn(tablePlader, SWT.NONE);
		tblclmnOprettet.setWidth(145);
		tblclmnOprettet.setText("Oprettet");
		new Label(shlErichsensPladesamling, SWT.NONE);

		populateFully(shlErichsensPladesamling);

		shlErichsensPladesamling.open();
		while (!shlErichsensPladesamling.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private static void populateFully(Shell shlErichsensPladesamling) {
		try {
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "admin");
			udenFilter = connection.prepareStatement("SELECT * FROM PLADE ORDER BY KUNSTNER");
			rs = udenFilter.executeQuery();

			while (rs.next()) {
				item = new TableItem(tablePlader, SWT.NONE);
				item.setText(0, rs.getString("FORLAG"));
				item.setText(1, rs.getString("NUMMER"));
				item.setText(2, rs.getString("KUNSTNER"));
				item.setText(3, rs.getString("TITEL"));
				item.setText(4, rs.getString("VOLUME"));
				item.setText(5, rs.getString("MEDIUM"));
				item.setText(6, rs.getString("ANTAL"));
				try {
					item.setText(7, rs.getString("AAR"));
				} catch (Exception e) {
					item.setText(7, "");
				}
				item.setText(8, rs.getString("OPRETTET"));
			}

		} catch (SQLException e) {
			MessageBox messageBox = new MessageBox(shlErichsensPladesamling, SWT.ICON_ERROR);
			messageBox.setMessage(e.getMessage());
			messageBox.open();
			e.printStackTrace();
		}
	}

}
