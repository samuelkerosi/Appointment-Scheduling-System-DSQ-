// node express mongodb is what I have to read
const express = require("express");
const mongoose = require("mongoose");
const bodyParser = require("body-parser");

const app = express();
const schedule = require("./src/routes/schedule");
app.use(bodyParser.json());

const person = require("./src/models/Person.js");
//app.use('/Person', Person); //TODO//
//mongodb stuff initialization
const mydatabase = require("./src/config/apikeys").mongoURI;

mongoose
  .connect(mydatabase)
  .then(() => console.log("my database is alive"))
  .catch(data => console.log(data));

//main api endponit
app.use("/api", schedule);

const port = process.env.PORT || 3001;

//app.listen(port, () => console.log(`server is running in port ${port}`));
app.listen(port, function() {
  console.log(`server is running in port ${port}`);
});
