package hu.szabobelazoltan.szbzbanktransactiondemo.datasources;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import hu.szabobelazoltan.szbzbanktransactiondemo.data.BankAccount;
import hu.szabobelazoltan.szbzbanktransactiondemo.data.BankTransaction;
import hu.szabobelazoltan.szbzbanktransactiondemo.exception.DemoApplicationException;
import hu.szabobelazoltan.szbzbanktransactiondemo.xml.XmlBankAccount;
import hu.szabobelazoltan.szbzbanktransactiondemo.xml.XmlBankAccounts;
import hu.szabobelazoltan.szbzbanktransactiondemo.xml.XmlResourceBankDataLoader;

public class XmlResourceAccountDataSource implements BankAccountDataSource {
	
	private final String resourceName;
		
	public XmlResourceAccountDataSource(String resourceName) {
		this.resourceName = resourceName;
	}

	public Map<String, BankAccount> getAccounts() throws DemoApplicationException {
		XmlResourceBankDataLoader loader = new XmlResourceBankDataLoader();
		XmlBankAccounts xmlBankAccounts = loader.loadBankData(resourceName, XmlBankAccounts.class);
		
		Map<String, BankAccount> accounts = new HashMap<String, BankAccount>();
		
		populate(xmlBankAccounts, accounts);
		
		return accounts;
	}
	
	private void populate(XmlBankAccounts xmlBankAccounts, Map<String, BankAccount> accounts) {
		for (XmlBankAccount xmlBankAccount : xmlBankAccounts.getAccounts()) {
			BankAccount bankAccount = new BankAccount();
			
			bankAccount.setAccountNumber(xmlBankAccount.getAccountNumber());
			bankAccount.setBalance(xmlBankAccount.getBalance());
			bankAccount.setCurrency(xmlBankAccount.getCurrency());
			bankAccount.setTransactions(new LinkedList<BankTransaction>());
			
			accounts.put(bankAccount.getAccountNumber(), bankAccount);
		}
	}

}
