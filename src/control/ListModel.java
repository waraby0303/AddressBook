package control;

import java.util.List;

import javax.swing.DefaultListModel;

/*************************************
 * 住所録データを保持するリストを管理する
 * ・リスト
 *************************************/

public class ListModel{
	private DefaultListModel<PersonalData> dataListModel;

	ListModel(){
		dataListModel = new DefaultListModel<PersonalData>();
	}
	
	/**
	 * データを追加する
	 * @param data 個人データ
	 */
	void dataAdd(PersonalData data) {
		dataListModel.addElement(data);
	}
	
	/**
	 * 複数データを変更する
	 * @param data 個人データ
	 * @param index インデックス番号
	 */
	void dataSet(PersonalData data,int index) {
		dataListModel.setElementAt(data, index);	
	}
	
	/**
	 * データを削除する
	 * @param listElement リスト
	 */
	void dataRemove(List<PersonalData> listElement) {
		for(PersonalData data:listElement)
			dataListModel.removeElement(data);
	}
	
	/**
	 * @return リストのサイズ
	 */
	int getListSize() {
		return dataListModel.getSize();
	}
	
	DefaultListModel<PersonalData> getListModel(){
		return dataListModel;
	}
	
	void setListModel(DefaultListModel<PersonalData> listModel) {
		dataListModel = listModel;
	}
	
}

