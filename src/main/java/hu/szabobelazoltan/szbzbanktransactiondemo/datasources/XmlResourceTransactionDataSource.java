package hu.szabobelazoltan.szbzbanktransactiondemo.datasources;

import java.util.ArrayList;
import java.util.List;

import hu.szabobelazoltan.szbzbanktransactiondemo.data.BankTransaction;
import hu.szabobelazoltan.szbzbanktransactiondemo.exception.DemoApplicationException;
import hu.szabobelazoltan.szbzbanktransactiondemo.xml.XmlBankTransaction;
import hu.szabobelazoltan.szbzbanktransactiondemo.xml.XmlBankTransactions;
import hu.szabobelazoltan.szbzbanktransactiondemo.xml.XmlResourceBankDataLoader;

public class XmlResourceTransactionDataSource implements BankTransactionDataSource {

	private final String resourceName;
		
	public XmlResourceTransactionDataSource(String resourceName) {
		super();
		this.resourceName = resourceName;
	}

	public List<BankTransaction> getTransactions() throws DemoApplicationException {
		XmlResourceBankDataLoader loader = new XmlResourceBankDataLoader();
		XmlBankTransactions xmlBankTransactions = loader.loadBankData(resourceName, XmlBankTransactions.class);
		
		List<BankTransaction> transactions = new ArrayList<BankTransaction>();
		
		populate(xmlBankTransactions, transactions);
		
		return transactions;
	}
	
	private void populate(XmlBankTransactions xmlBankTransactions, List<BankTransaction> transactions) {
		for (XmlBankTransaction xmlBankTransaction : xmlBankTransactions.getTransactions()) {
			BankTransaction bankTransaction = new BankTransaction();
			
			bankTransaction.setAccountNumber(xmlBankTransaction.getAccountNumber());
			bankTransaction.setAmount(xmlBankTransaction.getAmount());
			bankTransaction.setCurrency(xmlBankTransaction.getCurrency());
			bankTransaction.setExchangeRate(xmlBankTransaction.getExchangeRate());
			
			transactions.add(bankTransaction);
		}
	}

}
