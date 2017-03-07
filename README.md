# ProjectWebSecurity
3rd Year Project

Program to monitor for unauthorised changes to a website.

Web pages are hashed and the hashes stored in a database. The program monitors the website, getting hashes of the pages. It then compares the hashes with those in the database and if there is no match it retrieves the webserver access_log, finds the entry that has the same time (to within 1 minute) of the error time, extracts the IP addresses that was accessing the page at that time and sends all this information in an email to the administrator of the website.
