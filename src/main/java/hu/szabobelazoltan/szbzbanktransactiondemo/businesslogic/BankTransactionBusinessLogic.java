package hu.szabobelazoltan.szbzbanktransactiondemo.businesslogic;

import java.util.List;
import java.util.Map;

import hu.szabobelazoltan.szbzbanktransactiondemo.data.BankAccount;
import hu.szabobelazoltan.szbzbanktransactiondemo.data.BankTransaction;

public interface BankTransactionBusinessLogic {

	void process(Map<String, BankAccount> accounts, List<BankTransaction> transactions);
}
