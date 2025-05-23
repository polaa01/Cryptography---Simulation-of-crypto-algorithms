This is Java project which enables simulation of few crypto algorithms: Rail fence, Myszkowski, Playfair.
Simulation includes encryption of random text, which we create on input.
The user must register first.
On occasion registration, the user enters a username and password, after which the application
(automatically) issues a digital certificate and a pair of RSA keys for that user.

Users log in to the system in two steps. In the first step, it is necessary to enter
digital certificate, and in the second, username and password. After a successful login, the user
shows a list of available simulation algorithms. The user chooses the appropriate algorithm,
after which he enters the text he wants to encrypt (up to 100 characters), as well as the encryption key.
After encryption, the cipher is displayed to the user.
In addition, the result of each simulationinitiated by the user, is saved in a text file, in the format: TEXT | ALGORITHM | KEY
| CODE. In this way, a separate file with their history is created for each user
simulation. The content of your file can be seen only by the logged in user and only within
applications.
The application implies the existence of a public key infrastructure. 
All certificates are issued by a CA authority that was established before the start of the application.
