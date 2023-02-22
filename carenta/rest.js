const express = require("express");
const app = express();
const mysql = require("mysql");
const multer = require('multer');
const twilio = require('twilio');
const bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.use(bodyParser.raw());
app.use(express.static('public'));
const accountSid = 'AC57e223fca495960853cbf75bff12485a';
const authToken = 'f799ac1285d4468315c38ed3bbae27a4';
const client = twilio(accountSid, authToken);


var connection = mysql.createConnection({
    host     : 'localhost',
    user     : 'root',
    password : '',
    database:'carsdb'
});
connection.connect();

var storage;

// login
app.post('/login',function(req,res){

    var data = null    
    var query="select * from user where phonenumber=? and password=?"
    connection.query(query,[ req.body.phonenumber, req.body.password],function(error,results){
       if (error) {
           console.log(error)
           res.status(500).json({message:"server error"}) 
       }
       if(results.length>0) {
           data = results[0]
       }
       res.status(200).json(data)
   })
   });

// update user's information
app.post('/updateuser',function(req,res){

    var data = null    
    var query="UPDATE user SET fullname = ?, phonenumber = ?, password=? WHERE id=?"
    connection.query(query,[ req.body.fullname, req.body.phonenumber, req.body.password, req.body.id],function(error,results){
       if (error) {
           console.log(error)
           res.status(500).json({message:"server error"}) 
       }
       if(results.length>0) {
           data = results[0]
       }
       res.status(200).json(data)
   })
   });

 // get car
 app.get('/getcar',function(req,res){   
    var query="select * from car"
    connection.query(query,function(error,results){
       if (error) {
           console.log(error)
           res.status(500).json({message:"server error"}) 
       }
    
       res.status(200).json(results)
   })
   });


  // get reservation
 app.get('/getreservation',function(req,res){   
  var query="select * from reservation"
  connection.query(query,function(error,results){
     if (error) {
         console.log(error)
         res.status(500).json({message:"server error"}) 
     }
  
     res.status(200).json(results)
 })
 });

// adduser *sign up* the version with the image
app.post("/adduser",multer({storage: storage}).single('image'), function(req, res) { 
    var data = JSON.parse(req.body.user)
	var query="INSERT INTO user(fullname,phonenumber,password,birthdate,creditcard,expirationdate,drivinglicence) values (?,?,?,?,?,?,?)"
    connection.query(query,[data.fullname,data.phonenumber,data.password,data.birthdate,data.creditcard,data.expirationdate,"user"+req.file.filename],function(error,results){
       if (error) {
           console.log(error)
           res.status(500).json({message:"server error"}) 
       }
       sendMessage(data.password,data.phonenumber)
        res.status(200).json("success")
   })  
      })

// add reservation
app.post("/addreservation", function(req, res) { 
  const { id, id_car,id_user,dateDebut,dateFin,destination,source, pincode } = req.body;
  connection.query('INSERT INTO reservation (id, id_car,id_user,dateDebut,dateFin,destination,source, pincode) VALUES (?, ?, ?, ?, ?, ?, ?, ?)',
    [id, id_car, id_user, dateDebut, dateFin, destination, source, pincode]
    ,function(err,result) {
              if (err) {
                  console.log(err)
                  res.status(500).json({message:"server error"}) 
              }
              if (result) {
                  console.log(result);
                  res.status(200).json({
                      message: "reservation saved successfully",
                      data: result
                  })
              }
      
          })
          
      });


    
// funciton send message
function sendMessage(password,phonenum)
{
    client.messages
    .create({
      body: "your password is: "+password,
      from: '+16123548094', // my twilio number
      to: phonenum,
    })
    .then((message) => {
      //console.log(message.sid);
      console.log("sent successfully")
    })
    .catch((error) => {
      console.error(error);
});
}

var storage = multer.diskStorage({
  destination: function (req, file, cb) {
    cb(null, './public/user/')
  },
  filename: function (req, file, cb) {
    cb(null,Date.now()+"-"+file.originalname);
  }
})

const server = app.listen(8082,function() {
    const host = server.address().address
    const port = server.address().port
    console.log("Connected")
});


