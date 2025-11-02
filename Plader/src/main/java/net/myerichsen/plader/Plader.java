package net.myerichsen.plader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
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
	private static LocalResourceManager localResourceManager;
	private static Shell shlErichsensPladesamling_1;

	/**
	 * Launch the application.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = Display.getDefault();
		shlErichsensPladesamling_1 = new Shell();
		createResourceManager();
		shlErichsensPladesamling_1.setMinimumSize(new Point(1200, 380));
		shlErichsensPladesamling_1.setSize(1200, 684);
		shlErichsensPladesamling_1.setText("Erichsens pladesamling");
		shlErichsensPladesamling_1.setLayout(new GridLayout(1, false));

		Composite composite = new Composite(shlErichsensPladesamling_1, SWT.NONE);
		composite.setLayout(new RowLayout(SWT.HORIZONTAL));

		Button btnFiltrer = new Button(composite, SWT.NONE);
		btnFiltrer.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		btnFiltrer.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FilterDialog filterDialog = new FilterDialog(shlErichsensPladesamling_1, SWT.NONE);
				List<Plade> pladeListe = filterDialog.open(connection, tablePlader);
				tablePlader.removeAll();

				if (pladeListe != null) {
					for (Plade plade : pladeListe) {
						plade.addItem(tablePlader);
					}
				} else {
					populateFully(shlErichsensPladesamling_1);
				}
			}
		});
		btnFiltrer.setText("Filtrér");

		Button btnOpret = new Button(composite, SWT.NONE);
		btnOpret.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		btnOpret.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				OpretDialog opretDialog = new OpretDialog(shlErichsensPladesamling_1, SWT.NONE);
				Plade plade = opretDialog.open(connection);
				if (plade != null) {
					TableItem item2 = plade.addItem(tablePlader);
					tablePlader.showItem(item2);
				}
			}
		});
		btnOpret.setText("Opret");

		Label lblVlgEnRkke = new Label(composite, SWT.HORIZONTAL);
		lblVlgEnRkke.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		lblVlgEnRkke.setText("Vælg venligst en række, før du retter eller sletter");

		Button btnRet = new Button(composite, SWT.NONE);
		btnRet.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		btnRet.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem[] selection = tablePlader.getSelection();

				if (selection.length < 1) {
					MessageBox messageBox = new MessageBox(shlErichsensPladesamling_1, SWT.ICON_WARNING);
					messageBox.setMessage("Vælg venligst en række!");
					messageBox.open();
					return;
				}

				TableItem tableItem = selection[0];
				int i = tablePlader.getSelectionIndices()[0];
				OpdaterDialog opdaterDialog = new OpdaterDialog(shlErichsensPladesamling_1, SWT.NONE);
				Plade plade = opdaterDialog.open(connection, tableItem);

				if (plade != null) {
					tablePlader.remove(i);
					plade.addItem(tablePlader);
				}
			}
		});
		btnRet.setText("Ret");

		Button btnSlet = new Button(composite, SWT.NONE);
		btnSlet.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		btnSlet.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem[] selection = tablePlader.getSelection();

				if (selection.length < 1) {
					MessageBox messageBox = new MessageBox(shlErichsensPladesamling_1, SWT.ICON_WARNING);
					messageBox.setMessage("Vælg først en række!");
					messageBox.open();
				}

				TableItem tableItem = selection[0];
				int i = tablePlader.getSelectionIndices()[0];
				SletDialog sletDialog = new SletDialog(shlErichsensPladesamling_1, SWT.NONE);
				boolean slettet = sletDialog.open(connection, tableItem);
				if (slettet) {
					tablePlader.remove(i);
				}
			}
		});
		btnSlet.setText("Slet");

		tablePlader = new Table(shlErichsensPladesamling_1, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE);
		tablePlader.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		GridData gd_tablePlader = new GridData(SWT.FILL, SWT.TOP, true, true, 1, 1);
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
		tblclmnVolume.setWidth(65);
		tblclmnVolume.setText("Volume");

		TableColumn tblclmnMedium = new TableColumn(tablePlader, SWT.NONE);
		tblclmnMedium.setWidth(69);
		tblclmnMedium.setText("Medium");

		TableColumn tblclmnAntal = new TableColumn(tablePlader, SWT.NONE);
		tblclmnAntal.setWidth(50);
		tblclmnAntal.setText("Antal");

		TableColumn tblclmnAar = new TableColumn(tablePlader, SWT.NONE);
		tblclmnAar.setWidth(73);
		tblclmnAar.setText("År");

		TableColumn tblclmnOprettet = new TableColumn(tablePlader, SWT.NONE);
		tblclmnOprettet.setWidth(145);
		tblclmnOprettet.setText("Oprettet");
		new Label(shlErichsensPladesamling_1, SWT.NONE);

		populateFully(shlErichsensPladesamling_1);

		shlErichsensPladesamling_1.open();
		while (!shlErichsensPladesamling_1.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private static void createResourceManager() {
		localResourceManager = new LocalResourceManager(JFaceResources.getResources());
	}

	private static void populateFully(Shell shlErichsensPladesamling) {
		try {
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "admin");
			udenFilter = connection.prepareStatement("SELECT * FROM PLADE ORDER BY KUNSTNER, AAR");
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
