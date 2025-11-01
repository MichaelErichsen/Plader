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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

/**
 * Slet en plade fra pladesamlingen
 *
 * @author Michael Erichsen
 */
public class SletDialog extends Dialog {

	protected boolean result;
	protected Shell shlSlet;
	private Text textForlag;
	private Text textNummer;
	private Text textAar;
	private Text textOprettet;
	private Text textAntal;
	private Text textMedium;
	private Text textVolume;
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
	public SletDialog(Shell parent, int style) {
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
	public boolean open(Connection connection, TableItem tableItem) {
		createContents(connection, tableItem);
		shlSlet.open();
		shlSlet.layout();
		Display display = getParent().getDisplay();
		while (!shlSlet.isDisposed()) {
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
		shlSlet = new Shell(getParent(), SWT.SHELL_TRIM | SWT.TITLE);
		shlSlet.setSize(450, 468);
		shlSlet.setText("Slet en plade");
		shlSlet.setLayout(new GridLayout(2, false));

		Label lblForlag = new Label(shlSlet, SWT.NONE);
		lblForlag.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		lblForlag.setText("Forlag");

		textForlag = new Text(shlSlet, SWT.BORDER);
		textForlag.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		textForlag.setEditable(false);
		textForlag.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblNummer = new Label(shlSlet, SWT.NONE);
		lblNummer.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		lblNummer.setText("Nummer");

		textNummer = new Text(shlSlet, SWT.BORDER);
		textNummer.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		textNummer.setEditable(false);
		textNummer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblKunstner = new Label(shlSlet, SWT.NONE);
		lblKunstner.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		lblKunstner.setText("Kunstner");

		textKunstner = new Text(shlSlet, SWT.BORDER);
		textKunstner.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		textKunstner.setEditable(false);
		textKunstner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblTitel = new Label(shlSlet, SWT.NONE);
		lblTitel.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		lblTitel.setText("Titel");

		textTitel = new Text(shlSlet, SWT.BORDER);
		textTitel.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		textTitel.setEditable(false);
		textTitel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblVolume = new Label(shlSlet, SWT.NONE);
		lblVolume.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		lblVolume.setText("Volume");

		textVolume = new Text(shlSlet, SWT.BORDER);
		textVolume.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		textVolume.setEditable(false);
		textVolume.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblMedium = new Label(shlSlet, SWT.NONE);
		lblMedium.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		lblMedium.setText("Medium");

		textMedium = new Text(shlSlet, SWT.BORDER | SWT.READ_ONLY);
		textMedium.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		textMedium.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblAntal = new Label(shlSlet, SWT.NONE);
		lblAntal.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		lblAntal.setText("Antal");

		textAntal = new Text(shlSlet, SWT.BORDER);
		textAntal.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		textAntal.setEditable(false);
		textAntal.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblAar = new Label(shlSlet, SWT.NONE);
		lblAar.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		lblAar.setText("År");

		textAar = new Text(shlSlet, SWT.BORDER);
		textAar.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		textAar.setEditable(false);
		textAar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblOprettet = new Label(shlSlet, SWT.NONE);
		lblOprettet.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		lblOprettet.setText("Oprettet");

		textOprettet = new Text(shlSlet, SWT.BORDER);
		textOprettet.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
		textOprettet.setEditable(false);
		textOprettet.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		populateDialog(connection, tableItem);

		composite = new Composite(shlSlet, SWT.NONE);
		RowLayout rl_composite = new RowLayout(SWT.HORIZONTAL);
		rl_composite.pack = false;
		composite.setLayout(rl_composite);
		composite.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 2, 1));

				Button btnSlet = new Button(composite, SWT.NONE);
				btnSlet.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
				btnSlet.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						result = sletPlade(connection);
					}

				});
				btnSlet.setText("Slet");

						Button btnFortryd = new Button(composite, SWT.NONE);
						btnFortryd.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 12, SWT.NORMAL)));
						btnFortryd.addSelectionListener(new SelectionAdapter() {
							@Override
							public void widgetSelected(SelectionEvent e) {
								shlSlet.close();
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
		textVolume.setText(tableItem.getText(4));
		textMedium.setText(tableItem.getText(5));
		textAntal.setText(tableItem.getText(6));
		textAar.setText(tableItem.getText(7));
		textOprettet.setText(tableItem.getText(8));
	}

	/**
	 * Slet valgte plade
	 *
	 * @param connection
	 * @return
	 */
	private boolean sletPlade(Connection connection) {
		try {

			PreparedStatement statement = connection
					.prepareStatement("DELETE FROM PLADE WHERE FORLAG = ? AND NUMMER = ?");

			statement.setString(1, textForlag.getText());
			statement.setString(2, textNummer.getText());

			int rc = statement.executeUpdate();

			MessageBox messageBox = new MessageBox(shlSlet, SWT.ICON_INFORMATION);

			if (rc > 0) {
				messageBox.setMessage("Pladen er slettet");
			} else {
				messageBox.setMessage("Ingen plade er slettet");
			}
			messageBox.open();

			shlSlet.close();
			return true;
		} catch (SQLException e) {
			MessageBox messageBox = new MessageBox(shlSlet, SWT.ICON_ERROR);
			messageBox.setMessage(e.getMessage());
			messageBox.open();
			e.printStackTrace();
			return false;
		}
	}
}
