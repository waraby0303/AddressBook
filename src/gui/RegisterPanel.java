package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import control.PersonalData;

/*****************************
 * "新規登録"・"編集"画面を管理する
 ****************************/
public class RegisterPanel extends BasePanel {

	/* 各項目のラベルとテキストフィールド */
	private JLabel nameLbl;		private JTextField nameTxt;			// 名前
	private JLabel nameKanaLbl;	private JTextField nameKanaTxt;		// ふりがな
	private JLabel addressLbl;	private JTextField addressTxt;		// 住所
	private JLabel telLbl;		private JTextField telTxt;			// 電話番号
	private JLabel mailLbl;		private JTextField mailTxt;			// メールアドレス

	private JButton registerBtn = new JButton("登録");			// 登録ボタン
	private JButton cancelBtn = new JButton("キャンセル");		// キャンセルボタン

	private int panelChengeOpt;		// 画面オプション(0:新規登録 1:編集)
	private int index;				// 編集時のインデックス番号


	RegisterPanel(MainFrame m) {
		super(m);
		panelChengeOpt = 0;

		this.setLayout(null);

		// ラベルの設定
		nameLbl = new JLabel("　名　前：");
		nameLbl.setBounds(30, 30, 70, 30);

		nameKanaLbl = new JLabel("ふりがな：");
		nameKanaLbl.setBounds(30, 60, 70, 30);

		addressLbl = new JLabel("　住　所：");
		addressLbl.setBounds(30, 110, 70, 30);

		telLbl = new JLabel("　電　話：");
		telLbl.setBounds(30, 160, 70, 30);

		mailLbl = new JLabel("　メール：");
		mailLbl.setBounds(30, 190, 70, 30);


		// テキストフィールドの設定
		nameTxt = new JTextField(null);			// 名前
		nameTxt.setBounds(100,30,120,30);

		nameKanaTxt = new JTextField(null);		// ふりがな
		nameKanaTxt.setBounds(100,60,120,30);

		addressTxt = new JTextField(null);		// 住所
		addressTxt.setBounds(100,110,300,30);

		telTxt = new JTextField(null);			// 電話番号
		telTxt.setBounds(100, 160, 150, 30);

		mailTxt = new JTextField(null);			// メールアドレス
		mailTxt.setBounds(100,190,150,30);


		//登録ボタン
		registerBtn.setBounds(400,300,70,30);
		registerBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PersonalData data = mainFrame.getDataController().dataCreate(nameTxt.getText(), nameKanaTxt.getText(), addressTxt.getText(), telTxt.getText(), mailTxt.getText());
				//　新規登録画面
				if(mainFrame.getTitle().equals("新規登録")) {

					if(isEmptyTextChecked()==true) {

						// データが未入力の場合
						JOptionPane.showInternalMessageDialog(null, "データが入力されていません。");
					}else {

						// データが入力されている場合
						int yesOpt = JOptionPane.showConfirmDialog(registerBtn, "以下の内容で登録します。よろしいですか？\n" + mainFrame.getDataController().dataDisplay(data), null, JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
						if( yesOpt == JOptionPane.OK_OPTION) {

							// データ登録メソッドの呼び出し
							if(mainFrame.getDataController().dataRegister(data) == false) {
								JOptionPane.showInternalMessageDialog(null, "同じデータが既に存在します");
							}else {
								JOptionPane.showInternalMessageDialog(null, "登録が完了しました。");
								textClear();
							}
						}
					}

				//　編集画面
				}else if(mainFrame.getTitle().equals("編集")) {

					if(isEmptyTextChecked()==true) {

						// データが未入力の場合
						JOptionPane.showInternalMessageDialog(null, "データが入力されていません。");
					}else {int yesOpt = JOptionPane.showConfirmDialog(registerBtn, "以下の内容で登録します。よろしいですか？\n" + mainFrame.getDataController().dataDisplay(data), null, JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
						if( yesOpt == JOptionPane.OK_OPTION) {

							// データ変更メソッドの呼び出し
							if(mainFrame.getDataController().dataRevise(data,index) == false) {
								JOptionPane.showInternalMessageDialog(null, "同じデータが既に存在します");
							}else {
								JOptionPane.showInternalMessageDialog(null, "登録が完了しました。");
								textClear();
								mainFrame.panelChange(getPanel(), "list");
							}
						}
					}
				}
			}
		});



		//キャンセルボタン
		cancelBtn.setBounds(470,300,100,30);
		cancelBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isEmptyTextChecked()) {
					cancelPanelChenge();
				}else {
					int cancelOpt = JOptionPane.showConfirmDialog(cancelBtn, "入力内容は破棄されます。よろしいですか？", null, JOptionPane.OK_CANCEL_OPTION);
					if(cancelOpt == JOptionPane.OK_OPTION) {
						textClear();
						cancelPanelChenge();
					}
				}
			}
		});

		//各種コンポーネントの追加
		mainFrame.containerAddComp(this,nameLbl,nameTxt,nameKanaLbl,nameKanaTxt,addressLbl,addressTxt,telLbl,telTxt,mailLbl,mailTxt,registerBtn,cancelBtn);
	}

	/**
	 * @param opt 画面オプション(0:新規登録 1:編集)
	 */
	public void setPanelChengeOpt(int opt) {
		panelChengeOpt = opt;
	}

	//テキストフィールドの空白判定(キャンセルボタンのイベントに使用）
	private boolean isEmptyTextChecked() {
		System.out.println(nameTxt.getText());
		if("".equals(nameTxt.getText()) && "".equals(nameKanaTxt.getText()) && "".equals(addressTxt.getText())&& "".equals(telTxt.getText())&& "".equals(mailTxt.getText())) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * 編集するデータをテキストフィールドにセットする
	 * @param data 編集する個人データ
	 * @param index 編集するデータのインデックス番号
	 */
	public void reviseDataSet(PersonalData data,int index) {
		nameTxt.setText(data.getName());			// 名前
		nameKanaTxt.setText(data.getNameKana());	// ふりがな
		addressTxt.setText(data.getAddress());		// 住所
		telTxt.setText(data.getTel());				//電話番号
		mailTxt.setText(data.getMail());			// メールアドレス
		this.index = index;							// インデックス番号
	}

	/**
	 * テキストフィールドの値をクリアする
	 */
	private void textClear() {

		nameTxt.setText(null);		// 名前
		nameKanaTxt.setText(null);	// ふりがな
		addressTxt.setText(null);	// 住所
		telTxt.setText(null);		// 電話番号
		mailTxt.setText(null);		// メールアドレス

	}

	/**
	 * キャンセル時の画面遷移
	*/
	private void cancelPanelChenge() {
		if(panelChengeOpt == 0) {

			// メニュー画面に遷移する
			mainFrame.panelChange(getPanel(), "menu");

		}else if(panelChengeOpt == 1){

			// データ一覧画面に遷移する
			mainFrame.panelChange(getPanel(), "list");
			panelChengeOpt = 0;
		}
	}
}
