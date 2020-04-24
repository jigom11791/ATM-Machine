package atm_machine;

//import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;

public class Bank {
	private Vector<UserAccount> accounts = new Vector<UserAccount>();
	private UserAccount openAccount = null;// the account that we are using
	/**
	 * Constructor for Bank. It initializes the bank accounts.
	 */
	public Bank() {
		initAccounts();
	}
	/**
	 * Creates the bank accounts
	 */
	public void initAccounts() {
		accounts.add(new UserAccount("ChrisP","7433", 400,2000));
		accounts.add(new UserAccount("jigom117", "3972", 2000, 176));
		accounts.add(new UserAccount("kta779", "9326", 43, 3000));
		accounts.add(new UserAccount("Muhammed", "9275", 4366.45, 100000.00));
	}
	/**
	 * Access account if card number and pin number match an account.
	 * @param pin the pin number
	 * @param cardNumber the card number
	 * @return true if an account is accessed, false if an account is not accessed
	 */
	public boolean accessAccount(String pin, String cardNumber) {			
		Iterator<UserAccount> it = accounts.iterator();
					
		while(it.hasNext()) {
			UserAccount temp = it.next();
			if(temp.verifyPin(pin, cardNumber)) {
				openAccount = temp;
				return true;
			}
		}
		return false;	
	}
	/**
	 * Transfer money from one bank account to another bank account <br>
	 * A user account number has a length of 5. A bank account number is the user account number 
	 * with either a one or zero added to the end. 
	 * @param i the account 0 is checking account, 1 is savings account
	 * @param amount the amount that is being withdrawn from the user's account
	 * @param accountNumber the bank account number for the account being transfered to
	 * @return returns true if the transfer is successful and false if the transfer could not be completed
	 */
	public boolean transfer(int i, double amount, String accountNumber) {
		Iterator<UserAccount> it = accounts.iterator();
		UserAccount temp;
		int bankAccountType = Integer.parseInt(accountNumber.substring(accountNumber.length()-1));
		String aNum = accountNumber.substring(0, accountNumber.length()-1);
		
		if(withdraw(i,amount)) {
			while(it.hasNext()) {
				temp = it.next();
				//System.out.println(temp.getAccountNumber() + " " + aNum + " " + bankAccountType);
				if(temp.getAccountNumber().equals(aNum)) {
					temp.getAccount(bankAccountType).deposit(amount);
					return true;
				}
			}
			deposit(i, amount) //undo the money withdraw
			return false;
		}else {
			return false;
		}
		
	}
	/**
	 * Withdraws money from a bank account
	 * @param i
	 * @param amount
	 * @return
	 */
	public boolean withdraw(int i, double amount) {
		return openAccount.getAccount(i).withdraw(amount);
	}
	
	//deposit money method
	public void deposit(int i, double amount){
		openAccount.getAccount(i).deposit(amount);
	}
	
	//view balance
	/**
	 * Displays balance of specified account.
	 * @param option choice of account to view. 0 will view Checking; 1 will view Savings.
	 */
	public double viewBalance(int option) {
		BankAccount BA = openAccount.getAccount(option);
		return BA.getBalance();
	}
	/**
	 * Close Account<br>
	 * Sets open account to null once the user is done using the atm
	 * machine
	 */
	public void closeAccount() {
		openAccount = null;
	}
}
