Before running the application on localhost install mongo db and 
run lower mentioned commands in mongo shell

use crm -- to create crm db
db.createCollection("users")

then run lower mentioned commands using crm and admin mongo db
db.createUser({user:"username",pwd:"password",roles:["root"]})
db.createUser({user:"username",pwd:"password",roles:[{role:"readWrite",db:"crm"}],mechanisms:["SCRAM-SHA-1"]})

check mongo db user was succesfully created
db.auth('username','password')
