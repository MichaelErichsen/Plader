package net.myerichsen.plader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Opret en plade i en pladesamling
 *
 * @author Michael Erichsen
 */
public class OpretDialog extends Dialog {
	protected Plade result;
	protected Shell shlOpretEnNy;
	private Text textForlag;
	private Text textNummer;
	private Text textAar;
	private Text textOprettet;
	private Text textAntal;
	private Combo comboMedium;
	private Text textVolume;
	private Text textTitel;
	private Text textKunstner;
	private Connection connection;
	private Label lblKunstner;
	private Label lblTitel;
	private Label lblVolume;
	private Label lblMedium;
	private Label lblAntal;
	private Label lblAar;
	private Label lblOprettet;

	/**
	 * Create the dialog.
	 *
	 * @param parent
	 * @param style
	 */
	public OpretDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 *
	 * @param connection
	 * @param tablePlader
	 *
	 * @return the result
	 */
	public Plade open(Connection connection) {
		this.connection = connection;
		createContents();
		shlOpretEnNy.open();
		shlOpretEnNy.layout();
		Display display = getParent().getDisplay();
		while (!shlOpretEnNy.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shlOpretEnNy = new Shell(getParent(), getStyle());
		shlOpretEnNy.setSize(450, 323);
		shlOpretEnNy.setText("Opret en ny plade");
		shlOpretEnNy.setLayout(new GridLayout(2, false));

		Label lblForlag = new Label(shlOpretEnNy, SWT.NONE);
		lblForlag.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblForlag.setText("Forlag");

		textForlag = new Text(shlOpretEnNy, SWT.BORDER);
		textForlag.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblNummer = new Label(shlOpretEnNy, SWT.NONE);
		lblNummer.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNummer.setText("Nummer");

		textNummer = new Text(shlOpretEnNy, SWT.BORDER);
		textNummer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblKunstner = new Label(shlOpretEnNy, SWT.NONE);
		lblKunstner.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblKunstner.setText("Kunstner");

		textKunstner = new Text(shlOpretEnNy, SWT.BORDER);
		textKunstner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblTitel = new Label(shlOpretEnNy, SWT.NONE);
		lblTitel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblTitel.setText("Titel");

		textTitel = new Text(shlOpretEnNy, SWT.BORDER);
		textTitel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblVolume = new Label(shlOpretEnNy, SWT.NONE);
		lblVolume.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblVolume.setText("Volume");

		textVolume = new Text(shlOpretEnNy, SWT.BORDER);
		textVolume.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textVolume.addListener(SWT.KeyDown, event -> event.doit = Character.isDigit(event.character));

		lblMedium = new Label(shlOpretEnNy, SWT.NONE);
		lblMedium.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblMedium.setText("Medium");

		comboMedium = new Combo(shlOpretEnNy, SWT.BORDER | SWT.READ_ONLY);
		comboMedium.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		comboMedium.add("CD");
		comboMedium.add("LP");

		lblAntal = new Label(shlOpretEnNy, SWT.NONE);
		lblAntal.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblAntal.setText("Antal");

		textAntal = new Text(shlOpretEnNy, SWT.BORDER);
		textAntal.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textAntal.addListener(SWT.KeyDown, event -> event.doit = Character.isDigit(event.character));

		lblAar = new Label(shlOpretEnNy, SWT.NONE);
		lblAar.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblAar.setText("År");

		textAar = new Text(shlOpretEnNy, SWT.BORDER);
		textAar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textAar.addListener(SWT.KeyDown, event -> event.doit = Character.isDigit(event.character));

		lblOprettet = new Label(shlOpretEnNy, SWT.NONE);
		lblOprettet.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblOprettet.setText("Oprettet");

		textOprettet = new Text(shlOpretEnNy, SWT.BORDER);
		textOprettet.setEditable(false);
		textOprettet.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textOprettet.setText(LocalDate.now().toString());

		Button btnOpret = new Button(shlOpretEnNy, SWT.NONE);
		btnOpret.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				opretPlade();
			}

		});
		btnOpret.setText("Opret");

		Button btnFortryd = new Button(shlOpretEnNy, SWT.NONE);
		btnFortryd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlOpretEnNy.close();
			}
		});
		btnFortryd.setText("Fortryd");

	}

	/**
	 * Opret pladen i databasen
	 */
	private void opretPlade() {
		try {
			if (textForlag.getText().isEmpty() || textNummer.getText().isEmpty() || textKunstner.getText().isEmpty()
					|| textTitel.getText().isEmpty() || textVolume.getText().isEmpty()
					|| comboMedium.getText().isEmpty() || textAntal.getText().isEmpty()
					|| textAar.getText().isEmpty()) {
				MessageBox messageBox = new MessageBox(shlOpretEnNy, SWT.ICON_WARNING);
				messageBox.setMessage("Alle felter skal udfyldes!");
				messageBox.open();
				return;
			}

			String update = "INSERT INTO PLADE (FORLAG, NUMMER, KUNSTNER, TITEL, VOLUME, MEDIUM, ANTAL, AAR, OPRETTET) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement statement = connection.prepareStatement(update);

			statement.setString(1, textForlag.getText());
			statement.setString(2, textNummer.getText());
			statement.setString(3, textKunstner.getText());
			statement.setString(4, textTitel.getText());
			statement.setInt(5, Integer.parseInt(textVolume.getText()));
			statement.setString(6, comboMedium.getText());
			statement.setInt(7, Integer.parseInt(textAntal.getText()));
			statement.setInt(8, Integer.parseInt(textAar.getText()));
			statement.setString(9, textOprettet.getText());

			int rc = statement.executeUpdate();

			MessageBox messageBox = new MessageBox(shlOpretEnNy, SWT.ICON_INFORMATION);
			if (rc > 0) {
				messageBox.setMessage("Ny plade er oprettet");
				result = new Plade(textForlag.getText(), textNummer.getText(), textKunstner.getText(),
						textTitel.getText(), Integer.parseInt(textVolume.getText()), comboMedium.getText(),
						Integer.parseInt(textAntal.getText()), Integer.parseInt(textAar.getText()),
						textOprettet.getText());
			} else {
				messageBox.setMessage("Ny plade er ikke oprettet");
			}
			messageBox.open();

			shlOpretEnNy.close();
		} catch (

		SQLException e) {
			MessageBox messageBox = new MessageBox(shlOpretEnNy, SWT.ICON_ERROR);
			messageBox.setMessage(e.getMessage());
			messageBox.open();
			e.printStackTrace();
		}
	}
}
