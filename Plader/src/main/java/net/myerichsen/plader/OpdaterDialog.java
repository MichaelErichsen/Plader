package net.myerichsen.plader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

/**
 * Opdatér en plade i pladesamlingen
 *
 * @author Michael Erichsen
 */
public class OpdaterDialog extends Dialog {
	protected Plade result;
	protected Shell shlOpdater;
	private Text textForlag;
	private Text textNummer;
	private Spinner spinnerAar;
	private Text textOprettet;
	private Spinner spinnerAntal;
	private Combo comboMedium;
	private Spinner spinnerVolume;
	private Text textTitel;
	private Text textKunstner;
	private Label lblKunstner;
	private Label lblTitel;
	private Label lblVolume;
	private Label lblMedium;
	private Label lblAntal;
	private Label lblAar;
	private Label lblOprettet;
	private LocalResourceManager localResourceManager;
	private Composite composite;

	/**
	 * Create the dialog.
	 *
	 * @param parent
	 * @param style
	 */
	public OpdaterDialog(Shell parent, int style) {
		super(parent, style);
		createResourceManager();
		setText("SWT Dialog");
	}

	private void createResourceManager() {
		localResourceManager = new LocalResourceManager(JFaceResources.getResources());
	}

	/**
	 * Open the dialog.
	 *
	 * @param connection
	 * @param tableItem
	 *
	 * @return the result
	 */
	public Plade open(Connection connection, TableItem tableItem) {
		createContents(connection, tableItem);
		shlOpdater.open();
		shlOpdater.layout();
		Display display = getParent().getDisplay();
		while (!shlOpdater.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 *
	 * @param tableItem
	 * @param connection
	 */
	private void createContents(Connection connection, TableItem tableItem) {
		shlOpdater = new Shell(getParent(), SWT.SHELL_TRIM | SWT.TITLE);
		shlOpdater.setSize(450, 468);
		shlOpdater.setText("Opdatér en plade");
		shlOpdater.setLayout(new GridLayout(2, false));

		Label lblForlag = new Label(shlOpdater, SWT.NONE);
		lblForlag.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		lblForlag.setText("Forlag");

		textForlag = new Text(shlOpdater, SWT.BORDER);
		textForlag.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		textForlag.setEditable(false);
		textForlag.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblNummer = new Label(shlOpdater, SWT.NONE);
		lblNummer.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		lblNummer.setText("Nummer");

		textNummer = new Text(shlOpdater, SWT.BORDER);
		textNummer.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		textNummer.setEditable(false);
		textNummer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblKunstner = new Label(shlOpdater, SWT.NONE);
		lblKunstner.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		lblKunstner.setText("Kunstner");

		textKunstner = new Text(shlOpdater, SWT.BORDER);
		textKunstner.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		textKunstner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblTitel = new Label(shlOpdater, SWT.NONE);
		lblTitel.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		lblTitel.setText("Titel");

		textTitel = new Text(shlOpdater, SWT.BORDER);
		textTitel.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		textTitel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblVolume = new Label(shlOpdater, SWT.NONE);
		lblVolume.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		lblVolume.setText("Volume");

		spinnerVolume = new Spinner(shlOpdater, SWT.BORDER);
		spinnerVolume.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		spinnerVolume.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblMedium = new Label(shlOpdater, SWT.NONE);
		lblMedium.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		lblMedium.setText("Medium");

		comboMedium = new Combo(shlOpdater, SWT.BORDER | SWT.READ_ONLY);
		comboMedium.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		comboMedium.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		comboMedium.add("CD");
		comboMedium.add("LP");
		comboMedium.add("MC");

		lblAntal = new Label(shlOpdater, SWT.NONE);
		lblAntal.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		lblAntal.setText("Antal");

		spinnerAntal = new Spinner(shlOpdater, SWT.BORDER);
		spinnerAntal.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		spinnerAntal.setMinimum(1);
		spinnerAntal.setSelection(1);
		spinnerAntal.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblAar = new Label(shlOpdater, SWT.NONE);
		lblAar.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		lblAar.setText("År");

		spinnerAar = new Spinner(shlOpdater, SWT.BORDER);
		spinnerAar.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		spinnerAar.setMaximum(2030);
		spinnerAar.setMinimum(1948);
		spinnerAar.setSelection(1968);
		spinnerAar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblOprettet = new Label(shlOpdater, SWT.NONE);
		lblOprettet.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		lblOprettet.setText("Oprettet");

		textOprettet = new Text(shlOpdater, SWT.BORDER);
		textOprettet.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		textOprettet.setEditable(false);
		textOprettet.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		populateDialog(connection, tableItem);

		composite = new Composite(shlOpdater, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 2, 1));
		RowLayout rl_composite = new RowLayout(SWT.HORIZONTAL);
		rl_composite.pack = false;
		composite.setLayout(rl_composite);

		Button btnOpdater = new Button(composite, SWT.NONE);
		btnOpdater.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		btnOpdater.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				opdaterPlade(connection);
			}

		});
		btnOpdater.setText("Opdatér");

		Button btnFortryd = new Button(composite, SWT.NONE);
		btnFortryd.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		btnFortryd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlOpdater.close();
			}
		});
		btnFortryd.setText("Fortryd");

	}

	/**
	 * Udfyld dialogen
	 *
	 * @param connection
	 * @param tableItem
	 */
	private void populateDialog(Connection connection, TableItem tableItem) {
		textForlag.setText(tableItem.getText(0));
		textNummer.setText(tableItem.getText(1));
		textKunstner.setText(tableItem.getText(2));
		textTitel.setText(tableItem.getText(3));
		comboMedium.setText(tableItem.getText(5));
		spinnerAntal.setMinimum(1);
		spinnerAntal.setSelection(1);
		spinnerAar.setSelection(Integer.valueOf(tableItem.getText(7)));
		textOprettet.setText(tableItem.getText(8));
	}

	/**
	 * Opdatér valgte plade
	 *
	 * @param connection
	 */
	private void opdaterPlade(Connection connection) {
		try {

			PreparedStatement statement = connection.prepareStatement(
					"UPDATE PLADE SET KUNSTNER = ?, TITEL = ?, VOLUME = ?, MEDIUM = ?, ANTAL = ?, AAR = ? "
							+ "WHERE FORLAG = ? AND NUMMER = ?");

			statement.setString(1, textKunstner.getText());
			statement.setString(2, textTitel.getText());
			statement.setInt(3, Integer.parseInt(spinnerVolume.getText()));
			statement.setString(4, comboMedium.getText());
			statement.setInt(5, Integer.parseInt(spinnerAntal.getText()));
			statement.setInt(6, Integer.parseInt(spinnerAar.getText()));
			statement.setString(7, textForlag.getText());
			statement.setString(8, textNummer.getText());

			int rc = statement.executeUpdate();

			MessageBox messageBox = new MessageBox(shlOpdater, SWT.ICON_INFORMATION);

			if (rc > 0) {
				messageBox.setMessage("Pladen er opdateret");
				result = new Plade(textForlag.getText(), textNummer.getText(), textKunstner.getText(),
						textTitel.getText(), Integer.parseInt(spinnerVolume.getText()), comboMedium.getText(),
						Integer.parseInt(spinnerAntal.getText()), Integer.parseInt(spinnerAar.getText()),
						textOprettet.getText());
			} else {
				messageBox.setMessage("Ingen plade er opdateret");
			}
			messageBox.open();

			return;
		} catch (

		SQLException e) {
			MessageBox messageBox = new MessageBox(shlOpdater, SWT.ICON_ERROR);
			messageBox.setMessage(e.getMessage());
			messageBox.open();
			e.printStackTrace();
			return;
		}
	}
}
