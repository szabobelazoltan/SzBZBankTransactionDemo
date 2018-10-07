package hu.szabobelazoltan.szbzbanktransactiondemo.datasources;

import java.util.Map;

import hu.szabobelazoltan.szbzbanktransactiondemo.data.BankAccount;
import hu.szabobelazoltan.szbzbanktransactiondemo.exception.DemoApplicationException;

public interface BankAccountDataSource {

	Map<String, BankAccount> getAccounts() throws DemoApplicationException;
}
