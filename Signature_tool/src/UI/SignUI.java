package UI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

import javax.swing.*;

import Sign.Sign;

import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatIntelliJLaf;

public class SignUI extends JFrame {

	private Sign s;
	private JTextField txtHash;
	private JTextArea signa, privateKey;
	private JScrollPane pkPane;
	private int top = 20, left = 20, bottom = 20, right = 20;
	private Insets i = new Insets(top, left, bottom, right);

	public SignUI() {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					UIManager.setLookAndFeel(new FlatIntelliJLaf());
					UIManager.put("Label.font", UIManager.getFont("Label.font").deriveFont(Font.PLAIN, 16));
				} catch (UnsupportedLookAndFeelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				setTitle("Signature Tool");
				setLayout(new BorderLayout());
				add(new MainFrame());
				pack();
				setVisible(true);
				setLocationRelativeTo(null);
				setDefaultCloseOperation(EXIT_ON_CLOSE);
			}

		});

	}

	public class MainFrame extends JPanel {

		public MainFrame() {
			setBorder(new EmptyBorder(0, 0, 0, 0));
			setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weightx = 0;
			Content content = new Content();
			content.setBackground(new Color(36,38,39));
			add(content, gbc);
		}

	}

	public class Content extends JPanel implements ActionListener {

		public Content() {
			Dimension dmButton = new Dimension(30,40);
			Dimension dmLabel = new Dimension(130,40);
			setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();

			gbc.insets = i;
			gbc.weightx = 0.5;
			gbc.weighty = 0.5;

			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.EAST;
			JLabel lbHash = new JLabel("Hash Code: ");
			lbHash.setPreferredSize(dmLabel);
			lbHash.setFont(new Font("", Font.BOLD, 14));
			lbHash.setForeground(Color.orange);
			add(lbHash, gbc);

			gbc.gridx++;
			gbc.fill = GridBagConstraints.BOTH;
			gbc.anchor = GridBagConstraints.WEST;
			txtHash = new JTextField(40);
			txtHash.setPreferredSize(dmButton);
			txtHash.setBackground(Color.darkGray);
			txtHash.setForeground(Color.white);
			add(txtHash, gbc);

			gbc.gridx = 0;
			gbc.gridy += 1;
			gbc.anchor = GridBagConstraints.EAST;
			JLabel lbKey = new JLabel("Private Key: ");
			lbKey.setPreferredSize(dmLabel);
			lbKey.setFont(new Font("", Font.BOLD, 14));
			lbKey.setForeground(Color.orange);
			add(lbKey, gbc);

			gbc.gridx++;
			gbc.fill = GridBagConstraints.BOTH;
			gbc.anchor = GridBagConstraints.WEST;
			privateKey = new JTextArea(10, 10);
			privateKey.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			privateKey.setLineWrap(true);
			privateKey.setWrapStyleWord(true);
			privateKey.setBackground(Color.darkGray);
			privateKey.setForeground(Color.white);
			pkPane = new JScrollPane(privateKey);
			add(pkPane, gbc);

			gbc.gridy += 1;
			gbc.gridx = 1;
			JButton buttonSign = new JButton("Run");
			buttonSign.setBackground(Color.orange);
			buttonSign.setOpaque(true);
			buttonSign.setBorderPainted(false);
			buttonSign.setFont(new Font("", Font.BOLD, 14));
			buttonSign.setPreferredSize(dmButton);
			add(buttonSign, gbc);
			buttonSign.addActionListener(this);

			gbc.gridy++;
			gbc.gridx = 0;
			gbc.anchor = GridBagConstraints.EAST;
			JLabel lbSign = new JLabel("Your Signature: ");
			lbSign.setPreferredSize(dmLabel);
			lbSign.setFont(new Font("", Font.BOLD, 14));
			lbSign.setForeground(Color.orange);
			add(lbSign, gbc);

			gbc.fill = GridBagConstraints.BOTH;
			gbc.anchor = GridBagConstraints.WEST;
			gbc.gridx++;
			signa = new JTextArea(10, 10);
			signa.setLineWrap(true);
			signa.setWrapStyleWord(true);
			signa.setBackground(Color.darkGray);
			signa.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
			signa.setForeground(Color.white);
			pkPane = new JScrollPane(signa);
			add(pkPane, gbc);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getActionCommand().equalsIgnoreCase("Run")) {
				if (txtHash.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please type hash code!", "Error", JOptionPane.ERROR_MESSAGE);
				} else if (privateKey.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter the key provided!", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						s = new Sign();
						String signature = s.signing(txtHash.getText(), privateKey.getText());
						signa.setText(signature);
						JOptionPane.showMessageDialog(null, "You have successfully signed!", "Success",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception ed) {
						JOptionPane.showMessageDialog(null, "Invalid signature or hash!", "Error",
								JOptionPane.ERROR_MESSAGE);
					} 
				}
			}
		}
	}

	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException {
		SignUI s = new SignUI();
	}

}
