package hu.szabobelazoltan.szbzbanktransactiondemo;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import hu.szabobelazoltan.szbzbanktransactiondemo.businesslogic.BankTransactionBusinessLogic;
import hu.szabobelazoltan.szbzbanktransactiondemo.businesslogic.BankTransactionBusinessLogicImpl;
import hu.szabobelazoltan.szbzbanktransactiondemo.data.BankAccount;
import hu.szabobelazoltan.szbzbanktransactiondemo.data.BankTransaction;
import hu.szabobelazoltan.szbzbanktransactiondemo.datasources.BankAccountDataSource;
import hu.szabobelazoltan.szbzbanktransactiondemo.datasources.BankTransactionDataSource;
import hu.szabobelazoltan.szbzbanktransactiondemo.datasources.XmlResourceAccountDataSource;
import hu.szabobelazoltan.szbzbanktransactiondemo.datasources.XmlResourceTransactionDataSource;

public class BankTransactionBusinessLogicTest {

	@Test
	public void testProcessSingleTransaction() {
		final String accountNumber = "1111-2222";
		final Double balance = 100000d;
		final String currency = "HUF";
		
		final Double amount = 8000d;
		
		final Double expectedBalance = balance + amount;
		
		final BankAccount account = new BankAccount();
		account.setAccountNumber(accountNumber);
		account.setBalance(balance);
		account.setCurrency(currency);
		account.setTransactions(new LinkedList<>());
		
		final BankTransaction transaction = new BankTransaction();
		transaction.setAccountNumber(accountNumber);
		transaction.setCurrency(currency);
		transaction.setAmount(amount);
		transaction.setExchangeRate(null);
		
		final Map<String, BankAccount> accountMap = new HashMap<>();
		accountMap.put(accountNumber, account);
		
		final List<BankTransaction> transactionList = new ArrayList<>();
		transactionList.add(transaction);
		
		final BankTransactionBusinessLogic businessLogic = new BankTransactionBusinessLogicImpl((x) -> {}, (x) -> {});
		businessLogic.process(accountMap, transactionList);
		
		assertEquals(expectedBalance, account.getBalance());
	}

	private boolean invalidTransaction = false;
	
	@Test
	public void testInvalidTransaction() {
		final BankAccount account = new BankAccount();
		account.setAccountNumber("1111-2222");
		
		final BankTransaction transaction = new BankTransaction();
		transaction.setAccountNumber("1111-2223");
		
		final Map<String, BankAccount> accountMap = new HashMap<>();
		accountMap.put(account.getAccountNumber(), account);
		
		final List<BankTransaction> transactionList = new ArrayList<>();
		transactionList.add(transaction);
		
		final BankTransactionBusinessLogic businessLogic = new BankTransactionBusinessLogicImpl((x) -> {}, 
				(x) -> { this.invalidTransaction = true; });
		businessLogic.process(accountMap, transactionList);
		
		assertTrue(this.invalidTransaction);
	}
	
	@Test
	public void testProcessTransactionDifferentCurrencies() {
		final String accountNumber = "1111-2222";
		final Double balance = 100000d;
		final Double amount = -30d;
		final Double exchangeRate = 300d;
		
		final Double expectedBalance = balance + amount * exchangeRate;
		
		final BankAccount account = new BankAccount();
		account.setAccountNumber(accountNumber);
		account.setBalance(balance);
		account.setCurrency("HUF");
		account.setTransactions(new LinkedList<>());
		
		final BankTransaction transaction = new BankTransaction();
		transaction.setAccountNumber(accountNumber);
		transaction.setCurrency("USD");
		transaction.setAmount(amount);
		transaction.setExchangeRate(exchangeRate);
		
		final Map<String, BankAccount> accountMap = new HashMap<>();
		accountMap.put(accountNumber, account);
		
		final List<BankTransaction> transactionList = new ArrayList<>();
		transactionList.add(transaction);
		
		final BankTransactionBusinessLogic businessLogic = new BankTransactionBusinessLogicImpl((x) -> {}, (x) -> {});
		businessLogic.process(accountMap, transactionList);
		
		assertEquals(expectedBalance, account.getBalance());
	}
	
	private int notificationCounter = 0;
	
	@Test
	public void testNotifyTenTransactions() {
		final BankAccountDataSource accountDataSource = new XmlResourceAccountDataSource(getClass().getResourceAsStream("single_account.xml"));
		final BankTransactionDataSource transactionDataSource = new XmlResourceTransactionDataSource(getClass().getResourceAsStream("test_transactions.xml"));
		
		final Map<String, BankAccount> accountMap = accountDataSource.getAccounts();
		final List<BankTransaction> transactionList = transactionDataSource.getTransactions();
		
		final BankTransactionBusinessLogic businessLogic = new BankTransactionBusinessLogicImpl(
				(x) -> { notificationCounter++; }, (x) -> {});
		
		businessLogic.process(accountMap, transactionList);
		
		assertEquals(2, notificationCounter);
	}
}
