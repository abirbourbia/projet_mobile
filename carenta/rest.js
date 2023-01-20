const express = require("express");
const mysql = require("mysql");
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

// sohaib's test 1
app.get("/", function (req, res) {
    res.send("Welcome to the TP")
})

// sohaib's test 2
app.post("/data", function (req, res) {
    const data = req.body
    res.send(`Hello, I am ${data.name} and I am ${data.age} years old`)
})

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


// signup
app.post('/signup', function(req,res){
    const { id, fullname, phonenumber, password, birthdate, creditcard, expirationdate, drivinglicence } = req.body
    connection.query("INSERT into user values (?,?,?,?,?,?,?,?)", [id,fullname,phonenumber,password,birthdate,creditcard,expirationdate,drivinglicence]
    ,function(err,result) {
        if (err) {
            console.log(err)
            res.status(500).json({message:"server error"}) 
        }
        if (result) {
            console.log(result);
            res.status(200).json({
                message: "user signed up successfully",
                data: result
            })
        }

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


const server = app.listen(8082,function() {
    const host = server.address().address
    const port = server.address().port
    console.log("Connected")
});


