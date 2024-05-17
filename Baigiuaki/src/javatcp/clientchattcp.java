package javatcp;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.border.TitledBorder;
import javax.xml.transform.OutputKeys;

import view.Jframequanli;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class clientchattcp extends JFrame {

	private JPanel contentPane;
	private JTextField textfieldstaff;
	private JTextField textfieldip;
	private JTextField textfieldport;

	Socket mngsocket = null;
	String mngIP = "";
	int mngport = 0;
	String staffname = null;
	BufferedReader br = null;
	DataOutputStream os = null;
	OutputKeys t = null;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

					clientchattcp frame = new clientchattcp();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public clientchattcp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setBorder(
				new TitledBorder(null, "Staff and sever Info", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(1, 7, 0, 0));

		JLabel jlabelstaff = new JLabel("Staff: ");
		jlabelstaff.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(jlabelstaff);

		textfieldstaff = new JTextField();
		panel.add(textfieldstaff);
		textfieldstaff.setColumns(10);

		JLabel jlabelip = new JLabel("Mng IP: ");
		jlabelip.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(jlabelip);

		textfieldip = new JTextField();
		panel.add(textfieldip);
		textfieldip.setColumns(10);

		JLabel jlabelport = new JLabel("Port:  ");
		jlabelport.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(jlabelport);

		textfieldport = new JTextField();
		panel.add(textfieldport);
		textfieldport.setColumns(10);

		JFrame thisFrame = this;
		JButton jbuttonconnect = new JButton("Connect");
		jbuttonconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mngIP = textfieldip.getText();
				mngport = Integer.parseInt(textfieldport.getText());
				staffname = textfieldstaff.getText();
				try {

					mngsocket = new Socket(mngIP, mngport);
					if (mngsocket != null) {
						Jframequanli p = new Jframequanli(mngsocket, staffname, mngIP);
						thisFrame.getContentPane().add(p);
						p.getjtextareamanager().append("manager is running ");
						p.updateUI();

						br = new BufferedReader(new InputStreamReader(mngsocket.getInputStream()));
						os = new DataOutputStream(mngsocket.getOutputStream());
						os.writeBytes(staffname);
						os.write(13);
						os.write(10);
						os.flush();

					}

				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		panel.add(jbuttonconnect);
	}

}