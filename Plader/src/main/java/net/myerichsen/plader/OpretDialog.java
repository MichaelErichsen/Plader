package net.myerichsen.plader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Properties;

import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

/**
 * Opret en plade i en pladesamling
 *
 * @author Michael Erichsen
 */
public class OpretDialog extends Dialog {
	private static String propertyPath = System.getProperty("user.home") + File.separator + "plader.properties";
	private static Properties properties;
	protected Plade result;
	protected Shell shlOpretEnNy;
	private Text textForlag;
	private Text textNummer;
	private Spinner spinnerAar;
	private Text textOprettet;
	private Spinner spinnerAntal;
	private Combo comboMedium;
	private Spinner spinnerVolume;
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
	private LocalResourceManager localResourceManager;
	private Composite composite;
	private Label lblNewLabel;

	/**
	 * Create the dialog.
	 *
	 * @param parent
	 * @param style
	 */
	public OpretDialog(Shell parent, int style) {
		super(parent, style);
		createResourceManager();
		setText("SWT Dialog");

		try {
			properties = new Properties();
			properties.load(new FileInputStream(propertyPath));
		} catch (final Exception e) {
			try {
				properties.store(new FileWriter(propertyPath), "Plader");
				properties.load(new FileInputStream(propertyPath));
			} catch (final IOException e1) {
				final var messageBox = new MessageBox(shlOpretEnNy, SWT.ICON_WARNING);
				messageBox.setMessage(e1.getMessage());
				messageBox.open();
			}
		}
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shlOpretEnNy = new Shell(getParent(), SWT.SHELL_TRIM | SWT.TITLE);
		shlOpretEnNy.setSize(559, 468);
		shlOpretEnNy.setText("Opret en ny plade");
		shlOpretEnNy.setLayout(new GridLayout(2, false));

		final var lblForlag = new Label(shlOpretEnNy, SWT.NONE);
		lblForlag.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		lblForlag.setText("Forlag");

		textForlag = new Text(shlOpretEnNy, SWT.BORDER);
		textForlag.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.F3) {
					textForlag.setText(properties.getProperty("forlag", ""));
				}
			}
		});
		textForlag.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		textForlag.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final var lblNummer = new Label(shlOpretEnNy, SWT.NONE);
		lblNummer.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		lblNummer.setText("Nummer");

		textNummer = new Text(shlOpretEnNy, SWT.BORDER);
		textNummer.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		textNummer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblKunstner = new Label(shlOpretEnNy, SWT.NONE);
		lblKunstner.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		lblKunstner.setText("Kunstner");

		textKunstner = new Text(shlOpretEnNy, SWT.BORDER);
		textKunstner.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.F3) {
					textKunstner.setText(properties.getProperty("kunstner", ""));
				}
			}
		});
		textKunstner.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		textKunstner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblTitel = new Label(shlOpretEnNy, SWT.NONE);
		lblTitel.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		lblTitel.setText("Titel");

		textTitel = new Text(shlOpretEnNy, SWT.BORDER);
		textTitel.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		textTitel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblVolume = new Label(shlOpretEnNy, SWT.NONE);
		lblVolume.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		lblVolume.setText("Volume");

		spinnerVolume = new Spinner(shlOpretEnNy, SWT.BORDER);
		spinnerVolume.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		spinnerVolume.setSelection(0);
		spinnerVolume.setMinimum(0);
		spinnerVolume.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		spinnerVolume.addListener(SWT.KeyDown, event -> event.doit = Character.isDigit(event.character));

		lblMedium = new Label(shlOpretEnNy, SWT.NONE);
		lblMedium.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		lblMedium.setText("Medium");

		comboMedium = new Combo(shlOpretEnNy, SWT.BORDER | SWT.READ_ONLY);
		comboMedium.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		comboMedium.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		comboMedium.add("CD");
		comboMedium.add("LP");
		comboMedium.add("MC");

		lblAntal = new Label(shlOpretEnNy, SWT.NONE);
		lblAntal.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		lblAntal.setText("Antal");

		spinnerAntal = new Spinner(shlOpretEnNy, SWT.BORDER);
		spinnerAntal.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		spinnerAntal.setSelection(1);
		spinnerAntal.setMinimum(1);
		spinnerAntal.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		spinnerAntal.addListener(SWT.KeyDown, event -> event.doit = Character.isDigit(event.character));

		lblAar = new Label(shlOpretEnNy, SWT.NONE);
		lblAar.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		lblAar.setText("År");

		spinnerAar = new Spinner(shlOpretEnNy, SWT.BORDER);
		spinnerAar.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		spinnerAar.setMinimum(1948);
		spinnerAar.setMaximum(2030);
		spinnerAar.setSelection(1968);
		spinnerAar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblOprettet = new Label(shlOpretEnNy, SWT.NONE);
		lblOprettet.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		lblOprettet.setText("Oprettet");

		textOprettet = new Text(shlOpretEnNy, SWT.BORDER);
		textOprettet.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		textOprettet.setEditable(false);
		textOprettet.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textOprettet.setText(LocalDate.now().toString());

		composite = new Composite(shlOpretEnNy, SWT.NONE);
		composite.setLayout(new RowLayout(SWT.HORIZONTAL));
		composite.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 2, 1));

		lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		lblNewLabel.setText("F3 ved Forlag eller kunstner henter seneste  ");

		final var btnOpret = new Button(composite, SWT.NONE);
		btnOpret.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		btnOpret.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				opretPlade();
				shlOpretEnNy.close();
			}

		});
		btnOpret.setText("Opret");

		final var btnFortryd = new Button(composite, SWT.NONE);
		btnFortryd.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		btnFortryd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlOpretEnNy.close();
			}
		});
		btnFortryd.setText("Fortryd");

	}

	private void createResourceManager() {
		localResourceManager = new LocalResourceManager(JFaceResources.getResources());
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
		connection = this.connection = connection;
		createContents();
		shlOpretEnNy.open();
		shlOpretEnNy.layout();
		final var display = getParent().getDisplay();
		while (!shlOpretEnNy.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Opret pladen i databasen
	 */
	private void opretPlade() {
		try {
			if (textForlag.getText().isEmpty() || textNummer.getText().isEmpty() || textKunstner.getText().isEmpty()
					|| textTitel.getText().isEmpty() || spinnerVolume.getText().isEmpty()
					|| comboMedium.getText().isEmpty() || spinnerAntal.getText().isEmpty()
					|| spinnerAar.getText().isEmpty()) {
				final var messageBox = new MessageBox(shlOpretEnNy, SWT.ICON_WARNING);
				messageBox.setMessage("Alle felter skal udfyldes!");
				messageBox.open();
				return;
			}

			final var update = "INSERT INTO PLADE (FORLAG, NUMMER, KUNSTNER, TITEL, VOLUME, MEDIUM, ANTAL, AAR, OPRETTET) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

			final var statement = connection.prepareStatement(update);

			statement.setString(1, textForlag.getText().toUpperCase());
			statement.setString(2, textNummer.getText().toUpperCase());
			statement.setString(3, textKunstner.getText());
			statement.setString(4, textTitel.getText());
			statement.setInt(5, Integer.parseInt(spinnerVolume.getText()));
			statement.setString(6, comboMedium.getText());
			statement.setInt(7, Integer.parseInt(spinnerAntal.getText()));
			statement.setInt(8, Integer.parseInt(spinnerAar.getText()));
			statement.setString(9, textOprettet.getText());

			final var rc = statement.executeUpdate();

			final var messageBox = new MessageBox(shlOpretEnNy, SWT.ICON_INFORMATION);
			if (rc > 0) {
				messageBox.setMessage("Ny plade er oprettet");
				result = new Plade(textForlag.getText(), textNummer.getText(), textKunstner.getText(),
						textTitel.getText(), Integer.parseInt(spinnerVolume.getText()), comboMedium.getText(),
						Integer.parseInt(spinnerAntal.getText()), Integer.parseInt(spinnerAar.getText()),
						textOprettet.getText());
				properties.setProperty("forlag", textForlag.getText());
				properties.setProperty("kunstner", textKunstner.getText());

				try {
					properties.store(new FileWriter(propertyPath), "Plader");
				} catch (final IOException e) {
					e.printStackTrace();
				}
			} else {
				messageBox.setMessage("Ny plade er ikke oprettet");
			}
			messageBox.open();
			return;
		} catch (

		final SQLException e) {
			final var messageBox = new MessageBox(shlOpretEnNy, SWT.ICON_ERROR);
			messageBox.setMessage(e.getMessage());
			messageBox.open();
			e.printStackTrace();
			return;
		}
	}
}
