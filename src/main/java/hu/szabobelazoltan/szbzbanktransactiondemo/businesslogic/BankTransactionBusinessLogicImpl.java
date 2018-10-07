package hu.szabobelazoltan.szbzbanktransactiondemo.businesslogic;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import hu.szabobelazoltan.szbzbanktransactiondemo.data.BankAccount;
import hu.szabobelazoltan.szbzbanktransactiondemo.data.BankTransaction;

public class BankTransactionBusinessLogicImpl implements BankTransactionBusinessLogic {

	private int validTransactionCount = 0;
	
	private final Consumer<Collection<BankAccount>> onStatusReport;
	
	private final Consumer<BankTransaction> onInvalidTransaction;
		
	public BankTransactionBusinessLogicImpl(Consumer<Collection<BankAccount>> onStatusReportTransaction,
			Consumer<BankTransaction> onInvalidTransaction) {
		this.onStatusReport = onStatusReportTransaction;
		this.onInvalidTransaction = onInvalidTransaction;
	}

	public void process(Map<String, BankAccount> accounts, List<BankTransaction> transactions) {
		for (BankTransaction transaction : transactions) {
			BankAccount account = accounts.get(transaction.getAccountNumber());
			
			if (account != null) {
				updateAccountStatus(account, transaction);
				validTransactionCount++;
			} else {
				onInvalidTransaction.accept(transaction);
			}
			
			if (validTransactionCount > 0 && validTransactionCount % 10 == 0) {
				onStatusReport.accept(accounts.values());
			}
		}
	}
	
	private void updateAccountStatus(BankAccount account, BankTransaction transaction) {
		updateBalance(account, transaction);
		account.getTransactions().add(transaction);
	}
	
	private void updateBalance(BankAccount account, BankTransaction transaction) {
		Double newBalance;
		if (account.getCurrency().equals(transaction.getCurrency())) {
			newBalance = account.getBalance() + transaction.getAmount();
		} else {
			newBalance = account.getBalance() + transaction.getAmount() * transaction.getExchangeRate();
		}
		account.setBalance(newBalance);
	}
}
