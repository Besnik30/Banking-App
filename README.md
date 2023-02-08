Banking Application
Through this Banking Application we want to allow users of different types to create new bank
accounts, cards and perform different types of transactions.
The application should have the following roles and functionalities:
Basic login/logout functionality should be provided for all users.
USERS:
- ADMIN
- TELLER
- CLIENT
Admin can create/update/delete Tellers.
Teller can create/update/delete Clients.
New Bank Account Application:
Each bank account has a Bank Account ID, IBAN, Currency, Balance, Account Type(CURRENT,
TECHNICAL), Interest.
The client requests to open a new Bank Account, he only has the right to request a Current Account
and the Teller should be able to approve/disapprove the creation/activation of it.
“Current Account” transactions have a 0% of interest.
New Card Application:
Cards:
- DEBIT CARD
- CREDIT CARD
Debit Card: The client can make a request for a new Debit Card. The client must have an already
active Current Account which he has to select to connect the debit card with.
Credit Card: The client can make a request for a new Credit Card. For this card the user doesn’t have
to select any account, but he has to input his monthly income in the request. If the monthly income
is less than 500€, the request will be denied automatically.
Otherwise, the request should be sent to the Teller. When the teller approves it, he should input the
% of interest per transaction and a new Technical Account is automatically created to be connected
with this card.
A bank account cannot be connected with more than 1 card and vice versa, a card cannot be
connected with more than 1 account.
New Transaction:
A transaction has a Transaction ID, Bank Account ID, Amount, Currency, Type (DEBIT, CREDIT).
Debit (-) = Amount has been subtracted from the account where the transaction is performed.
Credit (+) = Amount has been added to the account where the transaction is performed.
When the client requests to perform a new transaction from his account to another account by
inputting the IBAN and amount he wants to transfer, some validations should be performed:
1. Balance Availability
The transaction shouldn’t be performed if there isn’t enough balance in the Current Account
(Debit Card), if the balance is subtracted from a TECHNICAL Account (Credit Card) the
transaction should be performed successfully and the account balance will result to a
negative value in case of insufficient funds.
2. Each account must be linked with at least one card.
3. If the transaction is performed from a Technical Account, the % of interest maintained by
the teller should be calculated and added to the subtracted amount.
The client can only see his own accounts, cards and transactions.
The teller can see everyone’s bank accounts and transactions.

Technologies:
 Java 11+
 Git for version control
 Maven for dependency management
 All CRUD APIs should be developed as REST APIs.
 Spring Web
 Spring Security (Include JWT)
 Spring Data JPA/Hibernate
 MySQL (or any other RDBMS)
 Logging
