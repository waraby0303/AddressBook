package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import control.PersonalData;


/************************************
 * "データ一覧"画面を管理する
 ************************************/
public class ListPanel extends BasePanel {

	/* メニューバー */
	private JMenuBar menubar = new JMenuBar();

	/* メニュー */
	private JMenu dataControlMenu = new JMenu("データ管理");
	private JMenu searchMenu = new JMenu("検索");
	private JMenu sortMenu = new JMenu("並べ替え");
	private JMenu otherMenu = new JMenu("その他");

	/* メニューアイテム */
	private JMenuItem register = new JMenuItem("新規登録");
	private JMenuItem revise = new JMenuItem("編集");
	private JMenuItem remove = new JMenuItem("削除");
	private JMenuItem nameSearch = new JMenuItem("名前検索");
	private JMenuItem ascendingSort = new JMenuItem("昇順");
	private JMenuItem descendingSort = new JMenuItem("降順");
	private JMenuItem menuPc = new JMenuItem("メニュー画面に戻る");
	private JMenuItem outputPc = new JMenuItem("データ出力");

	/* ボタン */
	private JButton reviseBtn = new JButton("編集");
	private JButton removeBtn = new JButton("削除");
	private JButton cancelBtn = new JButton("キャンセル");

	private JList<PersonalData> dataList = new JList<PersonalData>();

	ListPanel(MainFrame m) {
		super(m);
		this.setLayout(null);

		// メニュー関係のコンポーネントの追加
		mainFrame.containerAddComp(menubar, dataControlMenu,searchMenu,sortMenu,otherMenu);
		mainFrame.containerAddComp(dataControlMenu, register,revise,remove);
		mainFrame.containerAddComp(searchMenu, nameSearch);
		mainFrame.containerAddComp(sortMenu, ascendingSort,descendingSort);
		mainFrame.containerAddComp(otherMenu, menuPc,outputPc);

		// メニューバーの設定、追加
		menubar.setBounds(0, 0, 600, 25);
		this.add(menubar);

		// JListの設定、追加
		JScrollPane sp = new JScrollPane(dataList);
		sp.setBounds(50,40,500,300);
		this.add(sp);

		// ボタンの設定、追加
		reviseBtn.setBounds(380,340,70,30);		// 編集ボタン
		removeBtn.setBounds(380,340,70,30);		// 削除ボタン
		cancelBtn.setBounds(460,340,100,30);	// キャンセルボタン
		
		// ボタンをパネルに追加し、非表示にする
		mainFrame.containerAddComp(getPanel(), reviseBtn,cancelBtn,removeBtn);
		buttonHide(reviseBtn,cancelBtn,removeBtn);

		// "新規登録"メニューの処理
		register.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.getRegisterPanel().setPanelChengeOpt(1);
				mainFrame.panelChange(getPanel(), "register");
			}
		});

		// "編集"メニューの処理
		revise.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// メッセージボックスの表示
				JOptionPane.showInternalMessageDialog(null, "編集したい項目を選択し、編集ボタンを押してください。");

				// 編集・キャンセルボタンの配置
				buttonDisplay(reviseBtn,cancelBtn);

				// "編集"ボタンの処理
				reviseBtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {

						if(dataList.isSelectionEmpty()) {

							// 項目が選択されていない場合
							JOptionPane.showInternalMessageDialog(null, "項目が選択されていません。");

						}else {
							// 選択された項目のインデックス番号を取得する
							int[] indexes = dataList.getSelectedIndices();

							if(indexes.length > 1) {

								// 複数選択されていた場合
								JOptionPane.showInternalMessageDialog(null, "二人以上が選択されています。");

							}else {

								// 編集データを取得し、編集画面に遷移する
								PersonalData reviseData = dataList.getSelectedValue();
								mainFrame.panelChange(getPanel(), "revise");
								mainFrame.getRegisterPanel().setPanelChengeOpt(1);
								mainFrame.getRegisterPanel().reviseDataSet(reviseData, indexes[0]);

								// 編集・キャンセルボタンを非表示にする
								buttonHide(reviseBtn,cancelBtn);
							}
						}
					}
				});
				// "キャンセル"ボタンの処理
				cancelBtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {

						// 編集・キャンセルボタンを非表示にする
						buttonHide(reviseBtn,cancelBtn,removeBtn);
					}
				});
			}
		});

		// "削除"メニューの処理
		remove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				JOptionPane.showInternalMessageDialog(null, "削除したいデータを選択してボタンを押してください。");

				// 削除・キャンセルボタンを表示する
				buttonDisplay(removeBtn,cancelBtn);
				removeBtn.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if(dataList.isSelectionEmpty()) {

							// 選択されていない場合
							JOptionPane.showInternalMessageDialog(null, "項目が選択されていません。");

						}else {

							// 削除データを取得し、確認画面を表示する
							List<PersonalData> listElement = dataList.getSelectedValuesList();
							int opt = JOptionPane.showConfirmDialog(mainFrame, "選択された項目を削除します。よろしいですか？\n", null, JOptionPane.WARNING_MESSAGE);
							if(opt == JOptionPane.YES_OPTION) {
								mainFrame.getDataController().dataRemove(listElement);

								// 削除・キャンセルボタンを非表示にする
								buttonHide(removeBtn,cancelBtn);
							}
						}
					}
				});
			}
		});

		// "名前検索"メニューの処理
		nameSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String searchWord = JOptionPane.showInputDialog(mainFrame,"名前を入力してください");
				int[] indexes;						//インデックス番号

				if(!(searchWord.equals(null))) {
					// 該当データのインデックス番号を返すメソッドを呼び出す
					indexes = mainFrame.getDataController().nameSearch(searchWord);

					if(indexes[0] == -1) {
						// 該当データなしの場合
						JOptionPane.showInternalMessageDialog(null, "見つかりませんでした。");
					}else {
						// 該当データを選択状態にする
						dataList.setSelectedIndices(indexes);
					}
				}
			}
		});

		// "昇順"(並べ替え)メニューの処理
		ascendingSort.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// データをソートするメソッドの呼び出し
				mainFrame.getDataController().dataSort(0);
			}
		});

		// "降順"(並べ替え)メニューの処理
		descendingSort.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// データをソートするメソッドの呼び出し
				mainFrame.getDataController().dataSort(1);
			}
		});

		// "メニュー画面に戻る"メニューの処理
		 menuPc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 各種ボタンを非表示にし、メニュー画面に遷移する
				buttonHide(reviseBtn,removeBtn,cancelBtn);
				mainFrame.panelChange(getPanel(), "menu");
			}
		});

		 // "データ出力"メニューの処理
		 outputPc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 各種ボタンを非表示にし、データ出力画面に遷移する
				buttonHide(reviseBtn,removeBtn,cancelBtn);
				mainFrame.panelChange(getPanel(), "output");
			}
		});
	}

	/**
	 * @return JList
	 */
	public JList<PersonalData> getDataList() {
		return dataList;
	}
	
	public void setModel(DefaultListModel<PersonalData> listModel) {
		this.dataList.setModel(listModel);
	}

	/**
	 * ボタンを非表示にする
	 * @param buttons ボタン
	 */
	private void buttonHide(JButton... buttons) {
		for(JButton b:buttons) {
			b.setVisible(false);
		}
	}

	/**
	 * ボタンを表示する
	 * @param buttons ボタン
	 */
	private void buttonDisplay(JButton...buttons) {
		for(JButton b:buttons) {
			b.setVisible(true);
		}
	}

}
