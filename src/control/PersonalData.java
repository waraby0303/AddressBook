package control;
import java.io.Serializable;

/***********************************
 * 住所録中の１個人の情報<br>
 * ・名前<br>
 * ・ふりがな<br>
 * ・住所<br>
 * ・電話番号<br>
 * ・メールアドレス<br>
 **********************************/
public class PersonalData implements Serializable {
	
	private String name;		//名前
	private String nameKana;	//ふりがな
	private String address;		//住所
	private String tel;			//電話番号
	private String mail;		//メールアドレス
	
	protected PersonalData(String n,String nk,String a,String t,String m) {
		
		this.name = n;
		this.nameKana = nk;
		this.address = a;
		this.tel = t;
		this.mail = m;
		
	}
	
	
	@Override
	public String toString() {
		return this.name + "　/" + this.nameKana + "　/" + this.address + "　/" + this.tel + "　/" +  this.mail;
	}
	

	/**
	 * @return 名前
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return ふりがな
	 */
	public String getNameKana() {
		return nameKana;
	}
	
	/**
	 * @return 住所
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * @return 電話番号
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * @return メールアドレス
	 */
	public String getMail() {
		return mail;
	}
}
