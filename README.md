# ProjectWebSecurity
3rd Year Project

Program to monitor for unauthorised changes to a website.

Web pages are hashed and the hashes stored in a database. The program monitors the website, getting new hashes of the pages at set intervals. It then compares the new hashes with those in the database and if there is no match it retrieves the webserver access_log, finds the entry that has the same time (to within 1 minute) of the error time, extracts the IP addresses that were accessing the page at that time and sends all this information in an email to the administrator of the website. Then it restores the website from the backup and continues monitoring. If there is a second breach the program identifies whether the IP address is one that modified the website previously, and if this is found to be the case it adds the IP address to the .htaccess file preventing that IP address from accessing the website further.

Program source code: https://github.com/markbellingham/ProjectWebSecurity
