package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/*************************
 * "メニュー”画面を管理する
 ************************/
public class MenuPanel extends BasePanel {
	
	/* ボタン */
	private JButton registerBtn = new JButton("新規登録");
	private JButton listBtn = new JButton("データ一覧");
	private JButton outputBtn = new JButton("データ出力");

	MenuPanel(MainFrame m) {
		super(m);

		// "新規登録"ボタンの処理
		registerBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// "新規登録"画面に遷移する
				mainFrame.panelChange(getPanel(),"register");
			}
		});

		// "データ一覧"ボタンの処理
		listBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// "データ一覧"画面に遷移する
				mainFrame.panelChange(getPanel(),"list");
			}
		});

		// "データ出力"ボタンの処理
		outputBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// "データ出力"画面に遷移する
				mainFrame.panelChange(getPanel(),"output");
			}
		});

		// ボタンの追加
		mainFrame.containerAddComp(this,registerBtn,listBtn,outputBtn);
	}
}
