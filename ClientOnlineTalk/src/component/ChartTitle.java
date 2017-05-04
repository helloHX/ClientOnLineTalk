package component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;

import app.ClientOnlineTalk;
import connect.ClientHandler;
import util.FileUtil;
import view.ChartFrame;
import entity.Message;
import entity.User;

public class ChartTitle extends JPanel implements ActionListener {

	private static final long serialVersionUID = 5694960859045248531L;
	private String filepath;
	private User friend;
	private Color buttonColor = new Color(58, 173, 214);
	private Font font = new Font("宋体", Font.BOLD, 18);
	private Font infoFont = new Font("宋体", Font.BOLD, 60);
	private MyButton selectFile = new MyButton("选择文件", buttonColor, font);
	private MyButton selectImage = new MyButton("选择图片", buttonColor, font);
	private JLabel friendinfo = new JLabel();

	public ChartTitle() {

	}

	public ChartTitle(User friend) {
		this.friend = friend;
		this.setPreferredSize(new Dimension(800, 100));
		friendinfo.setText(friend.getUserName());
		friendinfo.setFont(infoFont);
		friendinfo.setForeground(new Color(58, 173, 214));
		initialize();
	}

	public void initialize() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
		selectFile.addActionListener(this);
		selectImage.addActionListener(this);
		this.add(friendinfo);
		this.add(selectFile);
		this.add(selectImage);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.filepath = FileUtil.ScanFile();
		if (filepath != null && !filepath.equals("")) {
			Date currentDate = new Date();
			String fileSentTime = currentDate.toLocaleString();
			Message message = new Message();
			message.setMessage(filepath);
			message.setMessageTime(fileSentTime);
			message.setFormId(ClientHandler.user.getUserID());
			message.setToId(friend.getUserID());

			ChartFrame charFrame = (ChartFrame) ClientOnlineTalk.chartFrameMap
					.get(friend.getUserID());
			charFrame.historyMassagePanel.PreAddMessage(message);
			if (e.getSource() == selectImage) {
				message.setMessageType("IMG");
				charFrame.historyMassagePanel.insertIcon(new File(filepath));
				charFrame.setVisible(true);
				ClientHandler.readySentImage(message);
			}
			if (e.getSource() == selectFile) {
				message.setMessageType("FILE");
				charFrame.historyMassagePanel
						.insertFilePanel(new File(filepath));
				ClientHandler.readySentFile(message);
			}
		}

	}
}
