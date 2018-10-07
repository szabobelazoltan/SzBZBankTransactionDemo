package hu.szabobelazoltan.szbzbanktransactiondemo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import hu.szabobelazoltan.szbzbanktransactiondemo.xml.XmlBankAccount;
import hu.szabobelazoltan.szbzbanktransactiondemo.xml.XmlBankAccounts;
import hu.szabobelazoltan.szbzbanktransactiondemo.xml.XmlBankTransaction;
import hu.szabobelazoltan.szbzbanktransactiondemo.xml.XmlBankTransactions;
import hu.szabobelazoltan.szbzbanktransactiondemo.xml.XmlResourceBankDataLoader;

public class XmlResourceBankDataLoaderTest {

	@Test
	public void testLoadingBankAccount() {
		final String expectedAccountNumber = "11111111-22222222";
		final String expectedCurrency = "HUF";
		final Double expectedBalance = 100000d;
		
		final XmlResourceBankDataLoader loader = new XmlResourceBankDataLoader();
		final XmlBankAccounts accounts = loader.loadBankData(getClass().getResourceAsStream("single_account.xml"), XmlBankAccounts.class);
		
		assertNotNull(accounts.getAccounts());
		assertEquals(1, accounts.getAccounts().size());
		
		final XmlBankAccount account = accounts.getAccounts().get(0);
		
		assertNotNull(account);
		assertEquals(expectedAccountNumber, account.getAccountNumber());
		assertEquals(expectedCurrency, account.getCurrency());
		assertEquals(expectedBalance, account.getBalance());
	}

	@Test
	public void testLoadingBankTransaction() {
		final String expectedAccountNumber = "11111111-22222222";
		final String expectedCurrency = "HUF";
		final Double expectedAmount = -10000d;
		
		final XmlResourceBankDataLoader loader = new XmlResourceBankDataLoader();
		final XmlBankTransactions transactions = loader.loadBankData(getClass().getResourceAsStream("single_transaction.xml"), XmlBankTransactions.class);
		
		assertNotNull(transactions.getTransactions());
		assertEquals(1, transactions.getTransactions().size());
		
		final XmlBankTransaction transaction = transactions.getTransactions().get(0);
		
		assertNotNull(transaction);
		assertEquals(expectedAccountNumber, transaction.getAccountNumber());
		assertEquals(expectedCurrency, transaction.getCurrency());
		assertEquals(expectedAmount, transaction.getAmount());
		assertNull(transaction.getExchangeRate());
	}
}
