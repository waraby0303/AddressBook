package control;

import java.util.List;

import javax.swing.DefaultListModel;

/*************************************
 * 住所録データを保持するリストを管理する
 * ・リスト
 *************************************/

public class ListModel{
	private DefaultListModel<PersonalData> dataListModel = new DefaultListModel<PersonalData>();

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

	/**
	 * データが存在するか確認する
	 * @param data 比較する個人データ
	 * @return 存在する場合はtrue,存在しない場合はfalse
	 */
	boolean isDataExistsChecked(PersonalData data) {
		for(int i = 0;i<dataListModel.getSize();i++) {

			// 個人データの内容が同じ場合はfalseを返す
			if(dataListModel.getElementAt(i).getName().equals(data.getName()) &&
					dataListModel.getElementAt(i).getNameKana().equals(data.getNameKana()) &&
					dataListModel.getElementAt(i).getAddress().equals(data.getAddress()) &&
					dataListModel.getElementAt(i).getTel().equals(data.getTel()) &&
					dataListModel.getElementAt(i).getMail().equals(data.getMail())) {
				return true;
			}
		}

		return false;
	}
}

