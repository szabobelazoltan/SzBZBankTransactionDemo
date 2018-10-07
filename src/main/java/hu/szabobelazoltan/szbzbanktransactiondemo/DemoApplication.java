package hu.szabobelazoltan.szbzbanktransactiondemo;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import hu.szabobelazoltan.szbzbanktransactiondemo.businesslogic.BankTransactionBusinessLogic;
import hu.szabobelazoltan.szbzbanktransactiondemo.businesslogic.BankTransactionBusinessLogicImpl;
import hu.szabobelazoltan.szbzbanktransactiondemo.data.BankAccount;
import hu.szabobelazoltan.szbzbanktransactiondemo.data.BankTransaction;
import hu.szabobelazoltan.szbzbanktransactiondemo.datasources.BankAccountDataSource;
import hu.szabobelazoltan.szbzbanktransactiondemo.datasources.BankTransactionDataSource;
import hu.szabobelazoltan.szbzbanktransactiondemo.datasources.XmlResourceAccountDataSource;
import hu.szabobelazoltan.szbzbanktransactiondemo.datasources.XmlResourceTransactionDataSource;
import hu.szabobelazoltan.szbzbanktransactiondemo.exception.DemoApplicationException;

public class DemoApplication {

	private static final String ACCOUNTS_XML_RESOURCE_NAME = "xml/accounts.xml";
	
	private static final String TRANSACTIONS_XML_RESOURCE_NAME = "xml/transactions.xml";
	
    public static void main( String[] args ) {
    	try {
    		BankAccountDataSource bankAccountDataSource = new XmlResourceAccountDataSource(DemoApplication.class.getResourceAsStream(ACCOUNTS_XML_RESOURCE_NAME));
        	BankTransactionDataSource bankTransactionDataSource = new XmlResourceTransactionDataSource(DemoApplication.class.getResourceAsStream(TRANSACTIONS_XML_RESOURCE_NAME));
        	
        	Map<String, BankAccount> accounts = bankAccountDataSource.getAccounts();
        	List<BankTransaction> transactions = bankTransactionDataSource.getTransactions();
        	
        	BankTransactionBusinessLogic businessLogic = new BankTransactionBusinessLogicImpl((accountList) -> printAccountStatuses(accountList), 
        			(transaction) -> printInvalidTransaction(transaction));
        	businessLogic.process(accounts, transactions);
        	
        	printAccountStatuses(accounts.values());
    	} catch (DemoApplicationException ex) {
    		ex.printStackTrace();
    	}
    }
    
    private static void printAccountStatuses(Collection<BankAccount> accounts) {
    	for (BankAccount account : accounts) {
    		System.out.println("---- Bank Account details ----");
    		System.out.println(String.format(" Account number: %s", account.getAccountNumber()));
    		System.out.println(String.format(" Balance: %.2f", account.getBalance()));
    		System.out.println(String.format(" Currency: %s", account.getCurrency()));
    		System.out.println(" -- Transactions --");
    		for (BankTransaction transaction : account.getTransactions()) {
    			System.out.println(String.format("  Amount: %.2f", transaction.getAmount()));
    			System.out.println(String.format("  Currency: %s", transaction.getCurrency()));
    			if (!account.getCurrency().equals(transaction.getCurrency())) {
        			System.out.println(String.format("  Exchange rate: %.2f", transaction.getExchangeRate()));    				
    			}
        		System.out.println();
    		}
    		System.out.println();
    	}
    }
    
    private static void printInvalidTransaction(BankTransaction transaction) {
    	System.out.println(String.format("WARNING - Account does not exist: %s", transaction.getAccountNumber()));
    }
}
