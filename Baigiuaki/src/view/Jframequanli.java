package view;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.border.TitledBorder;
import java.awt.GridLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class Jframequanli extends JPanel {
	Socket socket = null;
	BufferedReader br = null;
	DataOutputStream os = null; // day du lieu;
	Outputthread t = null;
	String sender;
	String receiver;
	JTextArea textAreamassagetren;

	public Jframequanli(Socket s, String sender, String receiver) {
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Message", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(1, 2, 0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);

		JTextArea textareamassage = new JTextArea();
		scrollPane.setViewportView(textareamassage);

		JButton buttonsend = new JButton("Send");
		buttonsend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textareamassage.getText().trim().length() == 0)
					return;
				try {
					os.writeBytes(textareamassage.getText());
					os.write(13);
					os.write(10);
					os.flush();
					textAreamassagetren.append("\n" + sender + ": " + textareamassage.getText());
					textareamassage.setText("");
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		panel.add(buttonsend);

		JScrollPane scrollPane_2 = new JScrollPane();
		add(scrollPane_2, BorderLayout.CENTER);

		textAreamassagetren = new JTextArea();
		scrollPane_2.setViewportView(textAreamassagetren);

		this.socket = s;
		this.sender = sender;
		this.receiver = receiver;

		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			os = new DataOutputStream(socket.getOutputStream());
			t = new Outputthread(s, textAreamassagetren, sender, receiver);
			t.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JTextArea getjtextareamanager() {
		return this.textAreamassagetren;

	}

}