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
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
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
	protected Shell shell;
	private Text textForlag;
	private Text textNummer;
	private Text textAar;
	private Text textOprettet;
	private Text textAntal;
	private Text textMedium;
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
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
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
		shell = new Shell(getParent(), getStyle());
		shell.setSize(450, 323);
		shell.setText("Opret en ny plade");
		shell.setLayout(new GridLayout(2, false));

		Label lblForlag = new Label(shell, SWT.NONE);
		lblForlag.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblForlag.setText("Forlag");

		textForlag = new Text(shell, SWT.BORDER);
		textForlag.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblNummer = new Label(shell, SWT.NONE);
		lblNummer.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNummer.setText("Nummer");

		textNummer = new Text(shell, SWT.BORDER);
		textNummer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblKunstner = new Label(shell, SWT.NONE);
		lblKunstner.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblKunstner.setText("Kunstner");

		textKunstner = new Text(shell, SWT.BORDER);
		textKunstner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblTitel = new Label(shell, SWT.NONE);
		lblTitel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblTitel.setText("Titel");

		textTitel = new Text(shell, SWT.BORDER);
		textTitel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblVolume = new Label(shell, SWT.NONE);
		lblVolume.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblVolume.setText("Volume");

		textVolume = new Text(shell, SWT.BORDER);
		textVolume.setEditable(false);
		textVolume.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textVolume.addListener(SWT.KeyDown, event -> event.doit = Character.isDigit(event.character));

		lblMedium = new Label(shell, SWT.NONE);
		lblMedium.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblMedium.setText("Medium");

		textMedium = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		textMedium.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblAntal = new Label(shell, SWT.NONE);
		lblAntal.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblAntal.setText("Antal");

		textAntal = new Text(shell, SWT.BORDER);
		textAntal.setEditable(false);
		textAntal.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textAntal.addListener(SWT.KeyDown, event -> event.doit = Character.isDigit(event.character));

		lblAar = new Label(shell, SWT.NONE);
		lblAar.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblAar.setText("År");

		textAar = new Text(shell, SWT.BORDER);
		textAar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textAar.addListener(SWT.KeyDown, event -> event.doit = Character.isDigit(event.character));

		lblOprettet = new Label(shell, SWT.NONE);
		lblOprettet.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblOprettet.setText("Oprettet");

		textOprettet = new Text(shell, SWT.BORDER);
		textOprettet.setEditable(false);
		textOprettet.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Button btnFiltrer = new Button(shell, SWT.NONE);
		btnFiltrer.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				result = filtrerPlade(connection, tablePlader);
			}

		});
		btnFiltrer.setText("Filtrér");

		Button btnFortryd = new Button(shell, SWT.NONE);
		btnFortryd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
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
		String[] values = new String[5];
		String[] fieldNames = new String[5];
		int aar = 0;
		int i = 0;
		PreparedStatement statement;
		ResultSet rs;

		if (textForlag.getText().isEmpty() && textNummer.getText().isEmpty() && textKunstner.getText().isEmpty()
				&& textTitel.getText().isEmpty() && textVolume.getText().isEmpty() && textMedium.getText().isEmpty()
				&& textAntal.getText().isEmpty() && textAar.getText().isEmpty()) {
			MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING);
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
		if (!textAar.getText().isBlank()) {
			fieldNames[i] = "AAR";
			values[i] = textAar.getText();
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

			shell.close();
			return list;
		} catch (

		SQLException e) {
			MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR);
			messageBox.setMessage(e.getMessage());
			messageBox.open();
			e.printStackTrace();

			return list;
		}
	}
}
