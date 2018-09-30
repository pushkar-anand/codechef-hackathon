
# codechef-hackathon-backend

##### SERVER REQUIREMENTS

<ul>
	<li>PHP >=7.2</li>
	<li>MySQL</li>
</ul>

### Steps to deploy:
1. Host a MySQL instance and use the mysql-dump.sql file to create required database and table.

2. Clone this repo to where you want to deploy this. Your virtual host document root should be the public folder of this repo.

3. Create an app on codechef and get your client secret, client id and enter the redirect url as https://your-domain/login/redirect

4. Find the file `define.php` in the includes folder and replace `BASE_APP_URL`, `DB_SERVER`,  `DB_USER`, `DB_PASSWORD`, `CODECHEF_CLIENT_ID`, `CODECHEF_CLIENT_SECRET`. 

5. Install the dependencies using composer.

6. Head over to [android-app](https://github.com/pushkar-anand/codechef-hackathon/tree/master/android-app). 
    

### Project directory explanation

<pre>
	<code>
php-backend/
├── composer.json 	#Composer packages and project information
├── composer.lock
├── controllers		#all the routes call these files to execute the route task
│   ├── contest
│   │   ├── list.long.php
│   │   ├── list.short.php
│   │   ├── problem.detail.php
│   │   └── problems.list.php
│   ├── home.php
│   ├── ide
│   │   ├── ide.run.php
│   │   └── ide.status.php
│   ├── login
│   │   ├── login.php
│   │   └── redirect.php
│   └── practice
│       ├── beginner.php
│       ├── challenge.php
│       ├── easy.php
│       ├── hard.php
│       ├── medium.php
│       ├── peer.php
│       └── problem.details.php
├── includes
│   ├── CodechefApiCall.php  #API Call helper
│   ├── Curl.php			#Curl Helper Class
│   ├── DB.php			#Database Helper Class
│   ├── define.php			#all the project constants and config information
│   ├── functions.php  		#all commonly used functions
│   └── User.php			#User helper Class to easily save, retrive user data.
├── logs
│   └── initiateLogger.php
├── mysql-dump.sql 		#MySQL database dump file
├── public				#all the public files
│   ├── apk
│   │   └── chefonphone.apk
│   ├── images
│   │   ├── app2.jpg
│   │   ├── app4.jpg
│   │   ├── app5.jpg
│   │   ├── app.jpg
│   │   └── logo.png
│   └── index.php		#entry point
├── README.md
├── routes   			#contains the api routes
│   └── route.php
└── views			#contains all mustache templates and html files
    ├── final.mustache
    ├── home.mustache
    ├── login.mustache
    └── scope-error.html
	</code>
</pre>


<br/>
## Route informations


###GET routes
	
##### returning HTML pages

`/` for homepage
	
`/login` for login page
`/login/redirect` for OAuth redirect
	


##### retuning json
All these routes call related Codechef API endpoint to fetch the data and then parse it to return relavant data. Call to these routes require a valid login token associated with a valid Codechef token and account.



`/contest/list/short` returns a json object of currently ongoing contest(Max 3)

`/contest/list/long` returns a json object of all the ongoing and future contests
 
`/contest/problems/list` returns a json of all problems for a particular contests

`/contest/problem/details` returns problem statement for a particular problem



`/practice/beginner` returns all problems under practice level beginner

`/practice/easy` returns all problems under practice level easy

`/practice/medium` returns all problems under practice level medium

`/practice/hard` returns all problems under practice level hard

`/practice/challenge` returns all problems under practice level challange

`/practice/peer` returns all problems under practice level peer



`/ide/status` returns compilations status of a code execution

#### POST routes

`/ide/run` receives the source code and language to run the code and returns a status code to check the compilation status.

