package gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import control.DataController;

/*************************
 * フレームを管理する<br>
 * 各種パネルを表示する<br>
 ************************/
public class MainFrame extends JFrame{

	private BasePanel menuPanel = new MenuPanel(this);			// メニュー画面
	private BasePanel registerPanel = new RegisterPanel(this);	// 新規登録画面
	private BasePanel listPanel = new ListPanel(this);			// データ一覧画面
	private BasePanel outputPanel = new OutputPanel(this);		// データ出力画面
	
	static DataController dataController;

	MainFrame(String title){
		dataController = new DataController(this);

		// フレームの設定
		this.setTitle(title);
		setSize(600,400);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowClosing());


		// パネルを追加し、"メニュー"以外の画面を非表示にする
		containerAddComp(this, menuPanel,registerPanel,listPanel,outputPanel);
		panelSetFalse(registerPanel,listPanel,outputPanel);


	}
	
	/**
	 * 閉じるボタンクリック時に確認ダイアログを表示する
	 */
	class WindowClosing extends WindowAdapter{
		@Override
		public void windowClosing(WindowEvent e) {
			int ans = JOptionPane.showConfirmDialog(MainFrame.this, "システムを終了します。よろしいですか？","システムの終了",JOptionPane.OK_CANCEL_OPTION);
			System.out.println(ans);
			if(ans == JOptionPane.YES_OPTION) {
				System.out.println("プログラムによるシステムの終了");
				System.exit(0);
			}
		}
	}

	public static void main(String[] args) {

		// フレームの生成、表示
		MainFrame mainFrame = new MainFrame("メニュー画面");
		mainFrame.setVisible(true);

		// データファイルが存在する場合はデータを読み込み、なければデータファイルを作成
		if(dataController.fileSearch()) {
			dataController.fileRead();
		}else {
			dataController.fileCreate();
		}
	}

	public RegisterPanel getRegisterPanel() {
		return (RegisterPanel) registerPanel;
	}
	
	public ListPanel getListPanel() {
		return (ListPanel) listPanel;
	}
	
	public DataController getDataController() {
		return dataController;
	}

	/**
	 * 画面遷移用。指定された名前のパネルを表示する
	 * @param panel 遷移元のパネル
	 * @param panelName 遷移先のパネルの名前
	 */
	void panelChange(BasePanel panel,String panelName) {
		
		// 遷移元のパネルを非表示にする
		panel.setVisible(false);
		
		// 遷移先の名前のパネルを表示する
		switch(panelName) {
		case "menu":
			menuPanel.setVisible(true);
			this.setTitle("メニュー画面");
			break;
		case "register":
			registerPanel.setVisible(true);
			this.setTitle("新規登録");
			break;
		case "revise":
			registerPanel.setVisible(true);
			this.setTitle("編集");
			break;
		case "list":
			listPanel.setVisible(true);
			this.setTitle("データ一覧");
			break;
		case "output":
			outputPanel.setVisible(true);
			this.setTitle("データ出力");
		}
	}
	
	/**
	 * コンテナにコンポーネントを追加する
	 * @param container 追加先のコンテナ
	 * @param comp 追加したいコンポーネント
	 */
	void containerAddComp(Container container,Component... comp) {
		for(Component c:comp) {
			container.add(c);
		}
	}

	/**
	 * パネルを非表示にする
	 * @param panel 非表示にするパネル
	 */
	void panelSetFalse(BasePanel... panel) {
		for(BasePanel p:panel) {
			p.setVisible(false);
		}
	}



}
