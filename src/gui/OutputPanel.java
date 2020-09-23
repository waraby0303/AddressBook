package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/****************************
 * "データ出力"画面を管理する
 ***************************/
public class OutputPanel extends BasePanel{

	private JLabel label = new JLabel("データをCSV形式で出力します。");
	private JButton outputBtn = new JButton("出力");
	private JButton menuBtn = new JButton("メニュー画面へ");
	private JButton listBtn = new JButton("データ一覧へ");

	OutputPanel(MainFrame m) {

		super(m);
		setLayout(null);

		// ラベルの設定
		label.setBounds(100,100,200,30);

		// "出力"ボタンの設定、処理
		outputBtn.setBounds(300,100,70,30);
		outputBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser filechooser = new JFileChooser() {

					@Override public void approveSelection() {
						//ファイルが存在する場合は確認画面を表示する
						File f = getSelectedFile();
					    if (f.exists() && getDialogType() == SAVE_DIALOG) {
					      String m = String.format(
					          "<html>%s はすでに存在します。上書きしますか？",
					          f.getAbsolutePath());
					      int rv = JOptionPane.showConfirmDialog(
					          this, m, "Save As", JOptionPane.YES_NO_OPTION);
					      if (rv != JOptionPane.YES_OPTION) {
					        return;
					      }
					    }
					    super.approveSelection();
					  }
					};

					// ファイルを指定するダイアログを表示
					int selected = filechooser.showSaveDialog(getPanel());
					if (selected == JFileChooser.APPROVE_OPTION){
						File file = filechooser.getSelectedFile();

						// ファイル出力メソッドを呼び出し、完了画面を表示する
						mainFrame.getDataController().csvOutput(file);
						JOptionPane.showInternalMessageDialog(null, "出力が完了しました。");
					}
			}
		});

		// "メニュー画面へ"ボタンの設定、処理
		menuBtn.setBounds(320,310,130,30);
		menuBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// メニュー画面に遷移する
				mainFrame.panelChange(getPanel(),"menu");
			}
		});

		// "データ一覧へ"ボタンの設定、処理
		listBtn.setBounds(460,310,130,30);
		listBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// データ一覧画面に遷移する
				mainFrame.panelChange(getPanel(),"list");
			}
		});

		// 各種コンポーネントの追加
		mainFrame.containerAddComp(this, label,outputBtn,menuBtn,listBtn);
	}


}
