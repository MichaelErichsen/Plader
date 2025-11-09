package net.myerichsen.plader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

/**
 * Filtrering af items
 *
 * @author Michael Erichsen
 */
public class FilterDialog extends Dialog {
	protected List<Plade> result;
	protected Shell shlFiltrerPlader;
	private Text textForlag;
	private Text textNummer;
	private Text spinnerAar;
	private Text textOprettet;
	private Text spinnerAntal;
	private Combo comboMedium;
	private Text spinnerVolume;
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
	private Table tablePlader;
	private LocalResourceManager localResourceManager;
	private Composite composite;
	private Button btnStarterMed;

	/**
	 * Create the dialog.
	 *
	 * @param parent
	 * @param style
	 */
	public FilterDialog(Shell parent, int style) {
		super(parent, style);
		createResourceManager();
		setText("SWT Dialog");
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shlFiltrerPlader = new Shell(getParent(), SWT.SHELL_TRIM | SWT.TITLE);
		shlFiltrerPlader.setSize(450, 468);
		shlFiltrerPlader.setText("Filtrér plader");
		shlFiltrerPlader.setLayout(new GridLayout(2, false));

		final var lblForlag = new Label(shlFiltrerPlader, SWT.NONE);
		lblForlag.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		lblForlag.setText("Forlag");

		textForlag = new Text(shlFiltrerPlader, SWT.BORDER);
		textForlag.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		textForlag.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final var lblNummer = new Label(shlFiltrerPlader, SWT.NONE);
		lblNummer.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		lblNummer.setText("Nummer");

		textNummer = new Text(shlFiltrerPlader, SWT.BORDER);
		textNummer.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		textNummer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblKunstner = new Label(shlFiltrerPlader, SWT.NONE);
		lblKunstner.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		lblKunstner.setText("Kunstner");

		textKunstner = new Text(shlFiltrerPlader, SWT.BORDER);
		textKunstner.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		textKunstner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblTitel = new Label(shlFiltrerPlader, SWT.NONE);
		lblTitel.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		lblTitel.setText("Titel");

		textTitel = new Text(shlFiltrerPlader, SWT.BORDER);
		textTitel.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		textTitel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblVolume = new Label(shlFiltrerPlader, SWT.NONE);
		lblVolume.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		lblVolume.setText("Volume");

		spinnerVolume = new Text(shlFiltrerPlader, SWT.BORDER);
		spinnerVolume.setEditable(false);
		spinnerVolume.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		spinnerVolume.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblMedium = new Label(shlFiltrerPlader, SWT.NONE);
		lblMedium.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		lblMedium.setText("Medium");

		comboMedium = new Combo(shlFiltrerPlader, SWT.BORDER | SWT.READ_ONLY);
		comboMedium.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		comboMedium.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		comboMedium.add("CD");
		comboMedium.add("LP");
		comboMedium.add("MC");

		lblAntal = new Label(shlFiltrerPlader, SWT.NONE);
		lblAntal.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		lblAntal.setText("Antal");

		spinnerAntal = new Text(shlFiltrerPlader, SWT.BORDER);
		spinnerAntal.setEditable(false);
		spinnerAntal.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		spinnerAntal.setSelection(1);
		spinnerAntal.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblAar = new Label(shlFiltrerPlader, SWT.NONE);
		lblAar.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		lblAar.setText("År");

		spinnerAar = new Text(shlFiltrerPlader, SWT.BORDER);
		spinnerAar.setEditable(false);
		spinnerAar.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		spinnerAar.setSelection(1968);
		spinnerAar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblOprettet = new Label(shlFiltrerPlader, SWT.NONE);
		lblOprettet.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		lblOprettet.setText("Oprettet");

		textOprettet = new Text(shlFiltrerPlader, SWT.BORDER);
		textOprettet.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		textOprettet.setEditable(false);
		textOprettet.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		composite = new Composite(shlFiltrerPlader, SWT.NONE);
		final var rl_composite = new RowLayout(SWT.HORIZONTAL);
		rl_composite.pack = false;
		composite.setLayout(rl_composite);
		composite.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 2, 1));

		btnStarterMed = new Button(composite, SWT.NONE);
		btnStarterMed.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		btnStarterMed.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				result = filtrerPladerStarts();
			}
		});
		btnStarterMed.setText("Starter med ....");

		final var btnIndeholder = new Button(composite, SWT.NONE);
		btnIndeholder.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		btnIndeholder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				result = filtrerPladeLike(connection, tablePlader);
			}

		});
		btnIndeholder.setText("Indeholder...");

		final var btnFortryd = new Button(composite, SWT.NONE);
		btnFortryd.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		btnFortryd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlFiltrerPlader.close();
			}
		});
		btnFortryd.setText("Fortryd");

	}

	private void createResourceManager() {
		localResourceManager = new LocalResourceManager(JFaceResources.getResources());
	}

	/**
	 * Filtrer udvalgte plader
	 *
	 * @param connection
	 * @param tablePlader
	 * @return
	 */
	private List<Plade> filtrerPladeLike(Connection connection, Table tablePlader) {
		final List<Plade> list = new ArrayList<>();
		final var values = new String[6];
		final var fieldNames = new String[6];
		var aar = 0;
		var i = 0;
		PreparedStatement statement;
		ResultSet rs;

		if (textForlag.getText().isEmpty() && textNummer.getText().isEmpty() && textKunstner.getText().isEmpty()
				&& textTitel.getText().isEmpty() && comboMedium.getText().isEmpty() && spinnerAar.getText().isEmpty()) {
			final var messageBox = new MessageBox(shlFiltrerPlader, SWT.ICON_WARNING);
			messageBox.setMessage("Mindst ét felt skal udfyldes!");
			messageBox.open();
			return list;
		}

		final var sb = new StringBuilder("SELECT * FROM PLADE WHERE LOWER (");

		if (!textForlag.getText().isBlank()) {
			fieldNames[i] = "FORLAG";
			values[i] = textForlag.getText();
			i++;
		}
		if (!textNummer.getText().isBlank()) {
			fieldNames[i] = "NUMMER";
			values[i] = textNummer.getText();
			i++;
		}
		if (!textKunstner.getText().isBlank()) {
			fieldNames[i] = "KUNSTNER";
			values[i] = textKunstner.getText();
			i++;
		}
		if (!textTitel.getText().isBlank()) {
			fieldNames[i] = "TITEL";
			values[i] = textTitel.getText();
			i++;
		}
		if (!comboMedium.getText().isBlank()) {
			fieldNames[i] = "MEDIUM";
			values[i] = comboMedium.getText();
			i++;
		}

		final var fieldNames2 = new String[i];

		for (var j = 0; j < i; j++) {
			fieldNames2[j] = fieldNames[j];
		}

		sb.append(String.join(") LIKE LOWER (?) AND LOWER (", fieldNames2));
		sb.append(") LIKE LOWER (?) ORDER BY KUNSTNER, AAR");

		final var query = sb.toString();

		try {
			statement = connection.prepareStatement(query);

			for (var j = 0; j < i; j++) {
				statement.setString(j + 1, "%" + values[j] + "%");
			}

			rs = statement.executeQuery();

			while (rs.next()) {
				try {
					aar = rs.getInt("AAR");
				} catch (final Exception e) {

				}

				final var plade = new Plade(rs.getString("FORLAG"), rs.getString("NUMMER"), rs.getString("KUNSTNER"),
						rs.getString("TITEL"), rs.getInt("VOLUME"), rs.getString("MEDIUM"), rs.getInt("ANTAL"), aar,
						rs.getString("OPRETTET"));

				list.add(plade);
			}

			shlFiltrerPlader.close();
			return list;
		} catch (

		final SQLException e) {
			final var messageBox = new MessageBox(shlFiltrerPlader, SWT.ICON_ERROR);
			messageBox.setMessage(e.getMessage());
			messageBox.open();
			e.printStackTrace();

			return list;
		}
	}

	private List<Plade> filtrerPladerStarts() {
		final List<Plade> list = new ArrayList<>();
		final var values = new String[6];
		final var fieldNames = new String[6];
		var aar = 0;
		var i = 0;
		PreparedStatement statement;
		ResultSet rs;

		if (textForlag.getText().isEmpty() && textNummer.getText().isEmpty() && textKunstner.getText().isEmpty()
				&& textTitel.getText().isEmpty() && comboMedium.getText().isEmpty() && spinnerAar.getText().isEmpty()) {
			final var messageBox = new MessageBox(shlFiltrerPlader, SWT.ICON_WARNING);
			messageBox.setMessage("Mindst ét felt skal udfyldes!");
			messageBox.open();
			return list;
		}

		final var sb = new StringBuilder("SELECT * FROM PLADE WHERE LOWER (");

		if (!textForlag.getText().isBlank()) {
			fieldNames[i] = "FORLAG";
			values[i] = textForlag.getText();
			i++;
		}
		if (!textNummer.getText().isBlank()) {
			fieldNames[i] = "NUMMER";
			values[i] = textNummer.getText();
			i++;
		}
		if (!textKunstner.getText().isBlank()) {
			fieldNames[i] = "KUNSTNER";
			values[i] = textKunstner.getText();
			i++;
		}
		if (!textTitel.getText().isBlank()) {
			fieldNames[i] = "TITEL";
			values[i] = textTitel.getText();
			i++;
		}
		if (!comboMedium.getText().isBlank()) {
			fieldNames[i] = "MEDIUM";
			values[i] = comboMedium.getText();
			i++;
		}

		final var fieldNames2 = new String[i];

		for (var j = 0; j < i; j++) {
			fieldNames2[j] = fieldNames[j];
		}

		sb.append(String.join(") LIKE LOWER (?) AND LOWER (", fieldNames2));
		sb.append(") LIKE LOWER (?) ORDER BY KUNSTNER, AAR");

		final var query = sb.toString();

		try {
			statement = connection.prepareStatement(query);

			for (var j = 0; j < i; j++) {
				statement.setString(j + 1, values[j] + "%");
			}

			rs = statement.executeQuery();

			while (rs.next()) {
				try {
					aar = rs.getInt("AAR");
				} catch (final Exception e) {

				}

				final var plade = new Plade(rs.getString("FORLAG"), rs.getString("NUMMER"), rs.getString("KUNSTNER"),
						rs.getString("TITEL"), rs.getInt("VOLUME"), rs.getString("MEDIUM"), rs.getInt("ANTAL"), aar,
						rs.getString("OPRETTET"));

				list.add(plade);
			}

			shlFiltrerPlader.close();
			return list;
		} catch (

		final SQLException e) {
			final var messageBox = new MessageBox(shlFiltrerPlader, SWT.ICON_ERROR);
			messageBox.setMessage(e.getMessage());
			messageBox.open();
			e.printStackTrace();

			return list;
		}

	}

	/**
	 * Open the dialog.
	 *
	 * @param connection
	 * @param tablePlader
	 * @return List<Plader>
	 */
	public List<Plade> open(Connection connection, Table tablePlader) {
		this.connection = connection;
		this.tablePlader = tablePlader;
		createContents();
		shlFiltrerPlader.open();
		shlFiltrerPlader.layout();
		final var display = getParent().getDisplay();
		while (!shlFiltrerPlader.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}
}
