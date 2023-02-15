const express = require("express");
const mysql = require("mysql");
const multer = require('multer')
const bodyParser = require('body-parser');
const app = express();
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.use(bodyParser.raw());
app.use(express.static('public'));

var connection = mysql.createConnection({
    host     : 'localhost',
    user     : 'root',
    password : '',
    database:'carsdb'
});
connection.connect();

var storage;

// login
 app.post('/login', function(req,res){
  
    connection.query("SELECT * from user where phonenumber=?", req.body.phonenumber, function(error,results){
               if (error) {
                   console.log(error)
                   res.status(500).json({message:"server error"}) 
               }
               if(results.length>0) {
                console.log(results)
                  var data = results[0]
                  console.log(data)
                  if (data.password === req.body.password) {
                    res.status(200).json({
                        message: "user logged in successfully",
                        data
                    })
                  } else {
                    res.status(401).json({message: "Wrong password"})
                  }
               }
               
           })
  });


// signup 01
// app.post('/signup', function(req,res){
//     const { id, fullname, phonenumber, password, birthdate, creditcard, expirationdate, drivinglicence } = req.body
//     connection.query("INSERT into user values (?,?,?,?,?,?,?,?)", [id,fullname,phonenumber,password,birthdate,creditcard,expirationdate,drivinglicence]
//     ,function(err,result) {
//         if (err) {
//             console.log(err)
//             res.status(500).json({message:"server error"}) 
//         }
//         if (result) {
//             console.log(result);
//             res.status(200).json({
//                 message: "user signed up successfully",
//                 data: result
//             })
//         }

//     })
    
// });

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

   var storage = multer.diskStorage({
    destination: function (req, file, cb) {
      cb(null, './public/user/')
    },
    filename: function (req, file, cb) {
      cb(null,Date.now()+"-"+file.originalname);
    }
  })
   


// adduser *sign up 02*

app.post("/adduser",multer({storage: storage}).single('image'), function(req, res) { 
    var data = JSON.parse(req.body.user)
	var query="INSERT INTO user(fullname,phonenumber,password,birthdate,creditcard,expirationdate,drivinglicence) values (?,?,?,?,?,?,?)"
    connection.query(query,[data.fullname,data.phonenumber,data.password,data.birthdate,data.creditcard,data.expirationdate,"user"+req.file.filename],function(error,results){
       if (error) {
           console.log(error)
           res.status(500).json({message:"server error"}) 
       }
        res.status(200).json("success")
   })  
      })

const server = app.listen(8082,function() {
    const host = server.address().address
    const port = server.address().port
    console.log("Connected")
});


