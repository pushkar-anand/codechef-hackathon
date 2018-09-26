# codechef-hackathon-backend

### Steps to deploy:
1. Host a MySQL instance and use the mysql-dump.sql file to create required database and table.
2. Clone this repo to where you want to deploy this. Your virtual host document root should be the public folder of this repo.
3. Create an app on codechef and get your client secret, client id and enter the redirect url as https://your-domain/login/redirect
4. Find the file `define.php` in the includes folder and replace `BASE_APP_URL`, `DB_SERVER`, `DB_PASSWORD`, `CODECHEF_CLIENT_ID`, `CODECHEF_CLIENT_SECRET`. 
5. Head over to [android-repo](https://github.com/pushkar-anand/codechef-hackthon-android.git). 
    