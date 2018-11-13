package bt.agent.sell.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import bt.agent.sell.SellerAgent;

public class SellerGui extends JFrame {
	private static final long serialVersionUID = 1L;
	private SellerAgent sellerAgent;
	private JTextField titleField, priceField;
	
	
	public SellerGui(SellerAgent agent) {
		super(agent.getLocalName());

		JPanel panel = new JPanel();
		
		this.sellerAgent = agent;
		panel.setLayout(new GridLayout(2, 2));
		
		panel.add(new JLabel("Tytul ksiazki:"));
		titleField = new JTextField(15);
		panel.add(titleField);
		
		panel.add(new JLabel("Cena $:"));
		priceField = new JTextField(15);
		panel.add(priceField);
		
		getContentPane().add(panel, BorderLayout.CENTER);

		JButton addButton = new JButton("Dodaj do katalogu");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {
					String title = titleField.getText().trim();
					String price = priceField.getText().trim();
					sellerAgent.updateCatalogue(title, Integer.parseInt(price));
					titleField.setText("");
					priceField.setText("");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(SellerGui.this, "Niepoprawna wartosc. " + e.getMessage(), "Blad", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		panel = new JPanel();
		panel.add(addButton);
		
		getContentPane().add(panel, BorderLayout.SOUTH);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				sellerAgent.doDelete();
			}
		});

		setResizable(false);
	}

	public void showGui() {
		pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX = (int) screenSize.getWidth() / 2;
		int centerY = (int) screenSize.getHeight() / 2;
		setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
		super.setVisible(true);
	}
}