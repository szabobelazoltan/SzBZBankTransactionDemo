package hu.szabobelazoltan.szbzbanktransactiondemo.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="transactions")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlBankTransactions {

	@XmlElement(name="transaction")
	private List<XmlBankTransaction> transactions;

	public List<XmlBankTransaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<XmlBankTransaction> transactions) {
		this.transactions = transactions;
	}
	
}
