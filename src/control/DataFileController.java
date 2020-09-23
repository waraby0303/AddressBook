package control;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;


/***********************************
 * ファイルの操作<br>
 * ・ファイル名（データ保存用）<br>
 * ・暗号・復号化キー<br>
 * ・暗号・復号化方式<br>
 * ・ファイルパス（データ保存用）<br>
 **********************************/
public class DataFileController {
	private final String PATH = "data.dat";					// ファイル名（データ保存用）
	private final String COMMON_KEY = "AddressBook_data";	// 暗号・復号化キー 128bit
	private final String CRYPT_ALGORITHM = "AES";			// 暗号・復号化方式
	private File file = new File(PATH);						// ファイルパス（データ保存用）

	/**
	 * データファイルを読み込む
	 * @return データファイルの内容<br>
	 * @throws NoSuchAlgorithmException 復号化方式が使用できない場合
	 * @throws NoSuchPaddingException	パディング方式が使用できない場合
	 * @throws InvalidKeyException		復号化キーが使用できない場合
	 */

	DefaultListModel<PersonalData> dataFileRead() throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException{
		// ファイルの中身が空か判定
		boolean emptyFileCheck = file.length() == 0;

		// 空でない場合、ファイルの中身を読み込む
		if(emptyFileCheck==false) {
			System.out.println("ファイルの読み込み");
			Cipher c = Cipher.getInstance(CRYPT_ALGORITHM);

			// 復号化モードで初期化
			c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(COMMON_KEY.getBytes(), CRYPT_ALGORITHM));

			// ファイルの読み込み
			try (FileInputStream f = new FileInputStream(file);
				BufferedInputStream b = new BufferedInputStream(f);
				CipherInputStream ci = new CipherInputStream(b, c);
				ObjectInputStream in = new ObjectInputStream(ci)){

				return (DefaultListModel<PersonalData>)in.readObject();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "ファイルが見つかりませんでした。処理を終了します。");
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "ファイルが読み込めませんでした。処理を終了します。");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "エラーが発生しました。処理を終了します。");
			}
		}
		// ファイルの中身が空の場合
		return null;
	}

	/**
	 * ファイルに書き込む
	 * @param list 書き込むデータのリスト
	 * @throws NoSuchAlgorithmException	暗号化方式が使用できない場合
	 * @throws NoSuchPaddingException	パディング方式が使用できない場合
	 * @throws InvalidKeyException		暗号化キーが使用できない場合
	 */
	void dataFileWrite(DefaultListModel<PersonalData> list) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
		Cipher c = Cipher.getInstance(CRYPT_ALGORITHM);

		// 暗号化モードで初期化
		c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(COMMON_KEY.getBytes(), CRYPT_ALGORITHM));

		// ファイルに書き込む
		try(FileOutputStream f = new FileOutputStream(PATH);
				BufferedOutputStream b = new BufferedOutputStream(f);
				CipherOutputStream co = new CipherOutputStream(b, c);
				ObjectOutputStream out = new ObjectOutputStream(co)){

			out.writeObject(list);
		}catch(IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "ファイルに書き込めませんでした。処理を終了します。");
		}
	}

	/**
	 * データファイルを新規作成する
	 */
	void dataFileCreate() {
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "エラーが発生しました。処理を終了します。");
		}
	}

	/**
	 * CSV形式でファイルに書き込む
	 * @param list 書き込むデータのリスト
	 * @param f 書き込み先のファイル
	 */
	void csvFileOutput(DefaultListModel<PersonalData> list,File f) {
		//出力ファイルの作成
		FileWriter fw;
		try {
			fw = new FileWriter(f);
			PrintWriter p = new PrintWriter(new BufferedWriter(fw));

			//ヘッダーを指定する
			p.print("名前");
			p.print(",");
			p.print("ふりがな");
			p.print(",");
			p.print("住所");
			p.print(",");
			p.print("電話番号");
			p.print(",");
			p.print("メールアドレス");
			p.println();

			//内容をセットする
			for(int i = 0;i<list.size();i++) {
				p.print(list.elementAt(i).getName());
				p.print(",");
				p.print(list.elementAt(i).getNameKana());
				p.print(",");
				p.print(list.elementAt(i).getAddress());
				p.print(",");
				p.print(list.elementAt(i).getTel());
				p.print(",");
				p.print(list.elementAt(i).getMail());
				p.println();
			}
			// ファイルに書き出し閉じる
            p.close();
            System.out.println("出力完了");
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "エラーが発生しました。処理を終了します。");
		}
	}

	File getFilePath() {
		return file;
	}

}
