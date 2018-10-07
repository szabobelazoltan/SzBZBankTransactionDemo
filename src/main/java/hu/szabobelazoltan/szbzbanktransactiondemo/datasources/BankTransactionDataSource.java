package hu.szabobelazoltan.szbzbanktransactiondemo.datasources;

import java.util.List;

import hu.szabobelazoltan.szbzbanktransactiondemo.data.BankTransaction;
import hu.szabobelazoltan.szbzbanktransactiondemo.exception.DemoApplicationException;

public interface BankTransactionDataSource {

	List<BankTransaction> getTransactions() throws DemoApplicationException;
}
