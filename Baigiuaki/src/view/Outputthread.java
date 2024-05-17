package view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JTextArea;

public class Outputthread extends Thread {
	Socket socket;
	JTextArea jtextarea;
	BufferedReader br;
	String sender;
	String receiver;

	Outputthread(Socket socket, JTextArea jtextarea, String sender, String receiver) {
		super();
		this.socket = socket;
		this.jtextarea = jtextarea;
		this.sender = sender;
		this.receiver = receiver;
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (true) {
			try {
				if (socket != null) {
					String msg = "";
					if ((msg = br.readLine()) != null && msg.length() > 0) {
						jtextarea.append("\n" + receiver + ": " + msg);
					}
					sleep(1000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}