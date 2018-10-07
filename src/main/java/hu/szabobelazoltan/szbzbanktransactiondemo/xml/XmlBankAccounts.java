package hu.szabobelazoltan.szbzbanktransactiondemo.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="accounts")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlBankAccounts {

	@XmlElement(name="account")
	private List<XmlBankAccount> accounts;

	public List<XmlBankAccount> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<XmlBankAccount> accounts) {
		this.accounts = accounts;
	}
	
}
