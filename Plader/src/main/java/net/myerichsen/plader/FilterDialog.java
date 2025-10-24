package net.myerichsen.plader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	private Table tablePlader;

	/**
	 * Create the dialog.
	 *
	 * @param parent
	 * @param style
	 */
	public FilterDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 *
	 * @return the result
	 */
	public List<Plade> open(Connection connection, Table tablePlader) {
		this.connection = connection;
		this.tablePlader = tablePlader;
		createContents();
		shlFiltrerPlader.open();
		shlFiltrerPlader.layout();
		Display display = getParent().getDisplay();
		while (!shlFiltrerPlader.isDisposed()) {
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
		shlFiltrerPlader = new Shell(getParent(), getStyle());
		shlFiltrerPlader.setSize(450, 323);
		shlFiltrerPlader.setText("Opret en ny plade");
		shlFiltrerPlader.setLayout(new GridLayout(2, false));

		Label lblForlag = new Label(shlFiltrerPlader, SWT.NONE);
		lblForlag.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblForlag.setText("Forlag");

		textForlag = new Text(shlFiltrerPlader, SWT.BORDER);
		textForlag.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblNummer = new Label(shlFiltrerPlader, SWT.NONE);
		lblNummer.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNummer.setText("Nummer");

		textNummer = new Text(shlFiltrerPlader, SWT.BORDER);
		textNummer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblKunstner = new Label(shlFiltrerPlader, SWT.NONE);
		lblKunstner.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblKunstner.setText("Kunstner");

		textKunstner = new Text(shlFiltrerPlader, SWT.BORDER);
		textKunstner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblTitel = new Label(shlFiltrerPlader, SWT.NONE);
		lblTitel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblTitel.setText("Titel");

		textTitel = new Text(shlFiltrerPlader, SWT.BORDER);
		textTitel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblVolume = new Label(shlFiltrerPlader, SWT.NONE);
		lblVolume.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblVolume.setText("Volume");

		spinnerVolume = new Spinner(shlFiltrerPlader, SWT.BORDER);
		spinnerVolume.setMinimum(1);
		spinnerVolume.setSelection(1);
		spinnerVolume.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		spinnerVolume.addListener(SWT.KeyDown, event -> event.doit = Character.isDigit(event.character));

		lblMedium = new Label(shlFiltrerPlader, SWT.NONE);
		lblMedium.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblMedium.setText("Medium");

		comboMedium = new Combo(shlFiltrerPlader, SWT.BORDER | SWT.READ_ONLY);
		comboMedium.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		comboMedium.add("CD");
		comboMedium.add("LP");

		lblAntal = new Label(shlFiltrerPlader, SWT.NONE);
		lblAntal.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblAntal.setText("Antal");

		spinnerAntal = new Spinner(shlFiltrerPlader, SWT.BORDER);
		spinnerAntal.setMinimum(1);
		spinnerAntal.setSelection(1);
		spinnerAntal.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		spinnerAntal.addListener(SWT.KeyDown, event -> event.doit = Character.isDigit(event.character));

		lblAar = new Label(shlFiltrerPlader, SWT.NONE);
		lblAar.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblAar.setText("År");

		spinnerAar = new Spinner(shlFiltrerPlader, SWT.BORDER);
		spinnerAar.setMaximum(2030);
		spinnerAar.setMinimum(1948);
		spinnerAar.setSelection(1968);
		spinnerAar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblOprettet = new Label(shlFiltrerPlader, SWT.NONE);
		lblOprettet.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblOprettet.setText("Oprettet");

		textOprettet = new Text(shlFiltrerPlader, SWT.BORDER);
		textOprettet.setEditable(false);
		textOprettet.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Button btnFiltrer = new Button(shlFiltrerPlader, SWT.NONE);
		btnFiltrer.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				result = filtrerPlade(connection, tablePlader);
			}

		});
		btnFiltrer.setText("Filtrér");

		Button btnFortryd = new Button(shlFiltrerPlader, SWT.NONE);
		btnFortryd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlFiltrerPlader.close();
			}
		});
		btnFortryd.setText("Fortryd");

	}

	/**
	 * Filtrer udvalgte plader
	 *
	 * @param connection
	 * @param tablePlader
	 * @return
	 */
	private List<Plade> filtrerPlade(Connection connection, Table tablePlader) {
		List<Plade> list = new ArrayList<>();
		String[] values = new String[6];
		String[] fieldNames = new String[6];
		int aar = 0;
		int i = 0;
		PreparedStatement statement;
		ResultSet rs;

		if (textForlag.getText().isEmpty() && textNummer.getText().isEmpty() && textKunstner.getText().isEmpty()
				&& textTitel.getText().isEmpty() && comboMedium.getText().isEmpty() && spinnerAar.getText().isEmpty()) {
			MessageBox messageBox = new MessageBox(shlFiltrerPlader, SWT.ICON_WARNING);
			messageBox.setMessage("Mindst ét felt skal udfyldes!");
			messageBox.open();
			return list;
		}

		StringBuilder sb = new StringBuilder("SELECT * FROM PLADE WHERE LOWER (");

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
		if (!spinnerAar.getText().isBlank()) {
			fieldNames[i] = "AAR";
			values[i] = spinnerAar.getText();
			i++;
		}

		String[] fieldNames2 = new String[i];

		for (int j = 0; j < i; j++) {
			fieldNames2[j] = fieldNames[j];
		}

		sb.append(String.join(") LIKE LOWER (?) AND LOWER (", fieldNames2));
		sb.append(") LIKE LOWER (?) ORDER BY KUNSTNER");

		String query = sb.toString();
		System.out.println(query);

		try {
			statement = connection.prepareStatement(query);

			for (int j = 0; j < i; j++) {
				statement.setString(j + 1, "%" + values[j] + "%");
			}

			rs = statement.executeQuery();

			while (rs.next()) {
				try {
					aar = rs.getInt("AAR");
				} catch (Exception e) {

				}

				Plade plade = new Plade(rs.getString("FORLAG"), rs.getString("NUMMER"), rs.getString("KUNSTNER"),
						rs.getString("TITEL"), rs.getInt("VOLUME"), rs.getString("MEDIUM"), rs.getInt("ANTAL"), aar,
						rs.getString("OPRETTET"));

				list.add(plade);
			}

			shlFiltrerPlader.close();
			return list;
		} catch (

		SQLException e) {
			MessageBox messageBox = new MessageBox(shlFiltrerPlader, SWT.ICON_ERROR);
			messageBox.setMessage(e.getMessage());
			messageBox.open();
			e.printStackTrace();

			return list;
		}
	}
}
