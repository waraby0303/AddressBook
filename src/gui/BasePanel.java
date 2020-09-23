package gui;

import javax.swing.JPanel;

/**********************
 * 各種パネルの親クラス
 *********************/
public class BasePanel extends JPanel{
	
	protected MainFrame mainFrame;

	BasePanel(MainFrame m) {
		
		mainFrame = m;

		// サイズ設定
		this.setSize(600,400);
	}

	public BasePanel getPanel() {
		return this;
	}
}
