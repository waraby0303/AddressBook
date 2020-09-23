package control;



import java.io.File;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import javax.crypto.NoSuchPaddingException;
import javax.swing.JOptionPane;

import gui.MainFrame;

/******************
 * 内部処理を行う
 ******************/
public class DataController {
	private MainFrame mainFrame;
	private ListModel listModel = new ListModel();
	private DataFileController dataFileController = new DataFileController();
	
	public DataController(MainFrame m){
		mainFrame = m;
	}

	/**
	 * 住所録に保存するデータを作成
	 * @param name 名前
	 * @param nameKana ふりがな
	 * @param address 住所
	 * @param tel 電話番号
	 * @param mail メールアドレス
	 * @return 個人情報
	*/
	public PersonalData dataCreate(String name,String nameKana,String address,String tel,String mail) {
		PersonalData data = new PersonalData(name,nameKana,address,tel,mail);
		return data;
	}

	/**
	 * 住所録にデータを保存する
	 * @param data 個人情報
	 */
	public void dataRegister(PersonalData data) {

		// データ登録メソッドの呼び出し
		listModel.dataAdd(data);

		// 追加後のデータをファイルに上書きする
		try {
			dataFileController.dataFileWrite(listModel.getListModel());
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "エラーが発生しました。処理を終了します。");
		}
		System.out.println("dataRegisterメソッドの実行");
	}



	/**
	 * 住所録の登録済みデータを変更する
	 * @param data 個人情報
	 * @param index インデックス番号
	 */
	public void dataRevise(PersonalData data,int index) {
		// データ変更メソッドの呼び出し
		listModel.dataSet(data,index);

		// 変更後のデータをファイルに上書きする
		try {
			dataFileController.dataFileWrite(listModel.getListModel());
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "エラーが発生しました。処理を終了します。");
		}
		System.out.println("dataReviseメソッドの実行");
	}


	/**
	 * 名前を検索する
	 * @param searchWord 検索する名前
	 * @return 該当データのインデックス番号
	 */
	public int[] nameSearch(String searchWord) {
		System.out.println("nameSearchメソッドの実行");
		int[] array = new int[listModel.getListSize()];							//インデックス番号保持用
		PersonalData[] dataArray = new PersonalData[listModel.getListSize()];	//データコピー用
		listModel.getListModel().copyInto(dataArray);

		int j = 0;	// 該当データの数をカウント

		// 検索処理
		for(int i = 0;i<dataArray.length;i++) {
			 if(dataArray[i].getName().matches(".*" + searchWord + ".*")) {
				 array[j] = i;
				 j++;
			}
		}
		// 該当データなしの場合は-1を返す
		if(j == 0) {
			int[] falseArray = {-1};
			return falseArray;
		}else {
			int[] indexes = new int[j];
			for(int i = 0;i<j;i++) {
				indexes[i] = array[i];
			}
			return indexes;
		}

	}


	/**
	 * 住所録の登録済みデータを削除する
	 * @param listElement 削除するデータのリスト
	 */
	public void dataRemove(List<PersonalData> listElement) {

		// データを削除する
		listModel.dataRemove(listElement);

		// 削除後のデータをファイルに上書きする
		try {
			dataFileController.dataFileWrite(listModel.getListModel());
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "エラーが発生しました。処理を終了します。");
		}
		System.out.println("dataRemoveメソッドの実行");
	}

	/**
	 * 住所録のデータリストをソートする
	 * @param opt ソートオプション(0:昇順 1:降順)
	 */
	public void dataSort(int opt) {

		// 仮のリストを作成し、データリストをコピーする
		List<PersonalData> tempList = new ArrayList<PersonalData>() {
		};
		for(int i = 0;i<listModel.getListSize();i++) {
			tempList.add(listModel.getListModel().getElementAt(i));
		}

		// 仮リストのソート
		Collections.sort(tempList,new Comparator<PersonalData>() {
			// ソートオプション
			@Override
			public int compare(PersonalData data1,PersonalData data2) {
				Collator collator = Collator.getInstance(Locale.JAPANESE);
		        return collator.compare(data1.getNameKana(), data2.getNameKana());
			}
		});

		// データリストを置換する
		if(opt == 0) {
			for(int i = 0;i<listModel.getListSize();i++) {
				listModel.getListModel().setElementAt(tempList.get(i), i);
			}
		}else if(opt == 1){
			// 降順にする
			Collections.reverse(tempList);
			for(int i = 0;i<listModel.getListSize();i++) {
				listModel.getListModel().setElementAt(tempList.get(i), i);
			}
		}

		// ソート後のデータをファイルに上書きする
		try {
			dataFileController.dataFileWrite(listModel.getListModel());
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "エラーが発生しました。処理を終了します。");
		}
	}


	/**
	 * データ保存用ファイルが存在するか確認する
	 * @return ファイルが存在する場合はtrue,なければfalse
	 */
	public boolean fileSearch() {
		File file = dataFileController.getFilePath();
		if(file.exists()) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * データ保存用ファイルを読み込む
	 * */
	public void fileRead() {

		// ファイル読み込みメソッドの呼び出し
		try {
			listModel.setListModel(dataFileController.dataFileRead());
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "エラーが発生しました。処理を終了します。");
		}
		//JListにDefaultListModelを追加
		mainFrame.getListPanel().setModel(listModel.getListModel());

	}

	public void fileCreate() {
		dataFileController.dataFileCreate();
	}

	public String dataDisplay(PersonalData data) {
		return "名前：" + data.getName() + "\nふりがな：" + data.getNameKana() + "\n住所：" + data.getAddress() + "\n電話番号：" + data.getTel() + "\nメールアドレス：" + data.getMail();
	}

	public void csvOutput(File f) {
		dataFileController.csvFileOutput(listModel.getListModel(),f);
	}

}
