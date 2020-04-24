package atm_machine;

import java.util.Scanner;
/**
 * 
 * @author Jose Gomez
 *
 */
public class ATM {
	private Bank myBank = new Bank();
	private Scanner cin = new Scanner(System.in);
	
	public ATM() {}
	/**
	 * This is the ATM welcome Screen<br>
	 * This screen is displayed until there is user input.
	 */
	public void welcomeScreen() {
		Screen.displayWelcome();
		String choice = cin.nextLine();
		if(choice.equalsIgnoreCase("Q")) {}
		else {
			//This should not be commented out unless testing
			insertCard();
			//For Testing purposes
			//myBank.accessAccount("7433", "00000");
			mainMenu();
		}
	}
	/**
	 * This method prompts user to input pin and card number (simulating inserting card and entering PIN).<br>
	 * If the user get it wrong 3 time, the ATM will return to the welcome screen.
	 */
	public void insertCard() {
		int tries = 0;
		String pin, cardNumber;
		boolean accountOpen = false;
		boolean cancel = false; 
		
		while(tries < 3 && !accountOpen && !cancel) {
			if(tries > 0) {
				System.out.print("Try Again? [y/n]: ");
				String choice = cin.nextLine();
				if(choice.equalsIgnoreCase("n")) {
					cancel = true;
				}
			}
			if(!cancel) {
				System.out.println("Insert card and PIN:");
				System.out.print("Card #: ");
				cardNumber = cin.nextLine();
				System.out.print("PIN: ");
				pin = cin.nextLine();
				accountOpen = myBank.accessAccount(pin, cardNumber);
				if(!accountOpen) {
					System.out.println("Account not found");
				}
				tries++;
			}
		}
		if(accountOpen) {
			mainMenu();
		}else {
			welcomeScreen();
		}
	}
	/**
	 * This is the main menu in the ATM. It allow user to select from withdraw funds, deposit funds, transferFunds
	 * and view balance. The ATM will come back here after each action until the user decides to end their usage of the atm.
	 * When the user finishes it goes back to welcome screen.<br><br>
	 * There is also a quit option. Should it be hidden? ATMs wouldn't normally turn off or end the program.
	 */
	public void mainMenu() {
		boolean quit = false;
		boolean cancel = false;
		
		while(!quit && !cancel) {
			Screen.displayMainMenu();
			String choice = cin.nextLine();
			switch(choice) {
			case "1": depositFunds(); break;
			case "2": withdrawFunds(); break;
			case "3": transferFunds(); break;
			case "4": viewBalance(); break;
			case "5": cancel = true;
				System.out.println("Please remove card");
				welcomeScreen();
				break;
			case "6": quit = true; break;
			default: System.out.println("Invalid Input");
			}
		}
	}
	/**
	 * Deposits check or cash to bank account
	 */
	public void depositFunds() {
		Screen.depositMenu();
		int choice = getChoice(); //choice of what account to use, savings or checking
		if(choice == 3) {
			System.out.println("Invalid Choice");
		}
		else if (choice != 2) {
			System.out.print("Would you like to deposit a check or cash? Type 1 for check, 2 for cash: ");
			int checkorcashoption = getChoice();
			if(checkorcashoption == 3) {
				System.out.println("Invalid Choice");
			}
			else if (checkorcashoption == 0) {// checking account 
				System.out.print("How much money is on the check?: ");
				String amountStr = cin.nextLine();
				try{
					double amount = Double.parseDouble(amountStr);
					myBank.deposit(choice, amount);
					System.out.println("Thank you for depositing the check!");
				}
				catch(Exception e)
				{
					System.out.println(e);
				}
			}
			//cash option
			else if (checkorcashoption == 1) {
	// 			Money m = new Money(); 
				System.out.println("How much cash would you like to deposit?");
				try{
					boolean checkedAmount = true;

					while(checkedAmount){
						String amnt = cin.nextLine();
						double amount = Double.parseDouble(amnt);
	// 					m.valueToMoney(amount);
						System.out.print("Is this the correct amount? Type Y for Yes, N for No: ");
						String confirmation = cin.nextLine();
						if(confirmation.equalsIgnoreCase("Y")) {
							myBank.deposit(choice, amount);
							System.out.println("Thank you for depositing the cash!"); 
							checkedAmount = false;
						}else {
							System.out.print("Please input the correct amount you wish to deposit: ");
						}
					}
				}catch(Exception e) {
					System.out.println("Invalid Input");
				}
			}
		}
		pressEnterToContinue();
	}
	/**
	 * Withdraws money from bank account. 
	 */
	public void withdrawFunds() {
		final double MIN_VALUE = 5;
		Screen.displayWithdrawMenu();
		int choice = getChoice();
		if(choice == 3) {
			System.out.println("Invalid Choice");
		}
		else if (choice != 2) {
			System.out.print("How much money would you like to withdraw?: ");
			String amtstr = cin.nextLine();
			try {
				double amount = Double.parseDouble(amtstr);
				if(amount >= MIN_VALUE && myBank.withdraw(choice, amount)) {
					giveMoney(amount);
				}else {
					System.out.println("Could not withdraw amount");
				}
			}catch(Exception e) {
				System.out.println(e);
			}
		}
		pressEnterToContinue();
	}
	/**
	 * Transfers funds from one account to another
	 */
	public void transferFunds() {
		final double MIN_VALUE = 5;
		Screen.transferMoneyMenu();
		int choice = getChoice();
		if(choice == 2) {}
		else if(choice == 3) {
			System.out.println("Invalid Choice");
		}
		else {
			System.out.print("How much money would you like to transfer?: ");
			String amtstr = cin.nextLine();
			System.out.println("Which account would you like to transfer to?: ");
			String aNumStr = cin.nextLine();
			try {
				int bankAccountType = Integer.parseInt(aNumStr.substring(aNumStr.length()-1));
				if(bankAccountType == 1 || bankAccountType == 0) {
					try {
				
						double amount = Double.parseDouble(amtstr);
						if(myBank.transfer(choice, amount, aNumStr)) {
							System.out.println("Transfer Sucessful");
						}else {
							System.out.println("Could not withdraw amount");
						}
					}catch(Exception e) {
						System.out.println(e);
					}
				} else {
					System.out.println("Invalid Account Number.");
				}
			}catch(Exception e) {
				System.out.println("Invalid Account Number.");
			}
		}
		pressEnterToContinue();
	}
	/**
	 * Shows the balance in a specific account
	 */
	public void viewBalance() {
		Screen.checkBalanceMoney();
		int choice = getChoice();
		if(choice == 1 || choice == 0) {
			System.out.printf("%nAccount: %s%nAmount: $%.2f%n", 
					  (choice == 0) ? "Checking" : "Savings", myBank.viewBalance(choice));
		}
		else if (choice == 3) {
			System.out.println("Invalid Choice");
		}
		pressEnterToContinue();
	}
	/**
	 * Gets the choice for the account. 0 is checking, 1 is savings, 2 corresponds to c/C which cancels the activity, 3 is an invalid option
	 * @return integer value. Each integer value corresponds to something different
	 */
	public int getChoice() {
		String choice = cin.nextLine();
		switch(choice) {
		case "1": return 0;
		case "2": return 1;
		case "c":
		case "C": return 2;
		default: return 3;
		}
	}
	/**
	 * Model of expelling the money through the slot in the atm
	 * @param amount the money value being withdrawn
	 */
	private void giveMoney(double amount) {
		int value[] = {5,10,20,50,100};
		int remainder = (int) amount;
		Money cash = new Money();
// 		if (remainder < 5) {
// 			cash.updateMoney(1, remainder);
// 			remainder = 0;
// 		}
		System.out.println("How would you like the money withdrawn: ");
		billsMenu(value, amount);
		System.out.print("ENTER NUMBER: ");
		String choicestr = cin.nextLine();
		try {
			int choice = Integer.parseInt(choicestr) - 1;
			if(value[choice] <= amount) {
				int tempamount = remainder / value[choice];
				remainder = remainder - (value[choice] * tempamount);
 				cash.valueToMoney(remainder); 
				cash.updateMoney(value[choice], tempamount);
				System.out.print("\nYou withdrew: " + String.format("$%.2f", amount) +
									"\nYour cash is: ");
				System.out.println(cash.toString());
			}else {
				System.out.println("Invalid Choice");
			}
		} catch(Exception e) {
			System.out.println("Invalid Input");
		}
	}
	/**
	 * Displays what bills you can choose from
	 * @param value
	 * @param amount
	 */
	private void billsMenu(int value[], double amount) {
		for(int i = 1; i-1 < 5; i++) {
			if(value[i-1] <= amount) {
				System.out.println(i + ") $" + value[i-1]);
			}
		}
	}
	/**
	 * Prompts user to press enter to continue
	 */
	private void pressEnterToContinue() {
		System.out.println("press return key to continue...");
		String choice = cin.nextLine();
	}
}
