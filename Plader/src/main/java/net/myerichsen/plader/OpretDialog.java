package net.myerichsen.plader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
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
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.RowLayout;

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
		shlOpretEnNy.setSize(450, 391);
		shlOpretEnNy.setText("Opret en ny plade");
		shlOpretEnNy.setLayout(new GridLayout(2, false));

		Label lblForlag = new Label(shlOpretEnNy, SWT.NONE);
		lblForlag.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		lblForlag.setText("Forlag");

		textForlag = new Text(shlOpretEnNy, SWT.BORDER);
		textForlag.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		textForlag.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblNummer = new Label(shlOpretEnNy, SWT.NONE);
		lblNummer.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		lblNummer.setText("Nummer");

		textNummer = new Text(shlOpretEnNy, SWT.BORDER);
		textNummer.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		textNummer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblKunstner = new Label(shlOpretEnNy, SWT.NONE);
		lblKunstner.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		lblKunstner.setText("Kunstner");

		textKunstner = new Text(shlOpretEnNy, SWT.BORDER);
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
		spinnerVolume.setSelection(1);
		spinnerVolume.setMinimum(1);
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
		spinnerAar.setMaximum(2030);
		spinnerAar.setMinimum(1948);
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
		RowLayout rl_composite = new RowLayout(SWT.HORIZONTAL);
		rl_composite.pack = false;
		composite.setLayout(rl_composite);
		composite.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 2, 1));

		Button btnOpret = new Button(composite, SWT.NONE);
		btnOpret.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		btnOpret.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				opretPlade();
			}

		});
		btnOpret.setText("Opret");
		
				Button btnFortryd = new Button(composite, SWT.NONE);
				btnFortryd.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
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
					|| textTitel.getText().isEmpty() || spinnerVolume.getText().isEmpty()
					|| comboMedium.getText().isEmpty() || spinnerAntal.getText().isEmpty()
					|| spinnerAar.getText().isEmpty()) {
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
			statement.setInt(5, Integer.parseInt(spinnerVolume.getText()));
			statement.setString(6, comboMedium.getText());
			statement.setInt(7, Integer.parseInt(spinnerAntal.getText()));
			statement.setInt(8, Integer.parseInt(spinnerAar.getText()));
			statement.setString(9, textOprettet.getText());

			int rc = statement.executeUpdate();

			MessageBox messageBox = new MessageBox(shlOpretEnNy, SWT.ICON_INFORMATION);
			if (rc > 0) {
				messageBox.setMessage("Ny plade er oprettet");
				result = new Plade(textForlag.getText(), textNummer.getText(), textKunstner.getText(),
						textTitel.getText(), Integer.parseInt(spinnerVolume.getText()), comboMedium.getText(),
						Integer.parseInt(spinnerAntal.getText()), Integer.parseInt(spinnerAar.getText()),
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
