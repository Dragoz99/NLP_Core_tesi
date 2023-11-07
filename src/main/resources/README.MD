# NLP_Core_tesi
README.md
---------------------------------------------------------------------
REQUISITI
---------------------------------------------------------------------

per avviare il programma è neccessario possedere questi requisiti iniziali:
- IceSrcum
- MySQL 5.7
- Java jdk 18

---------------------------------------------------------------------
Installazione del programma passo dopo passo
---------------------------------------------------------------------
1) installare MySQL 5.7
   è importante la versione 5.7 perchè riesce a comunicare con il server di ICESCRUM


Sito per il download	https://dev.mysql.com/downloads/mysql/5.7.html

2) installare ICESCRUM
   durante la fase di installazione di icescrum è OBBLIGATORIO configurare il Database managment
   System.
   per tutte le informazioni neccessarie per l'installazione corretta setuite queta guida
   https://www.icescrum.com/documentation/how-to-install-icescrum/

insintesi bisogna:
- creare il database di icescrum all'interno di MySQL
- avviare l'installazione di icescrum fornedo i parametri di icescrum.
3) Creare il database del programma

all'interno della cartella di installazione è presente il file "databaseUserStories.sql"
che contiene i comandi DDL per la creazione del database in MySQL.
E' IMPORTANTE creare IL Database CON il nome "userstorydb".


NOTE IMPORTANTI:

porta server : 1099
porta client : 1100




---------------------------------------------------------------------
Avvio programma
---------------------------------------------------------------------
per utilizzare il programma è neccesario prima di tutto

1) avviare IceSrcum per creare le user stories
2) avviare ServerUS
3) avviare ClinetUS


---------------------------------------------------------------------