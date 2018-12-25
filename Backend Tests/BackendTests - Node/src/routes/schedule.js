const express = require("express");
const router = express.Router();

// database stuff

const Person = require("../models/Person");

// API endpoint

// (req, res) => { .. } is equivalent to funciton(req, res){ .. }

//test from tutorial
exports.Person = function(req, res){
  res.send('Test controller');
}

router.get("/", (req, res) => {
  Person.find().then(people => res.json(people));
});

router.post("/checkin", (req, res) => {
  const newCheckin = new Person({
    fname: req.body.fname,
    lname: req.body.lname
  });

  newCheckin.save().then(data => res.json(data));
});

router.delete("/patient/:id", (req, res) => {
  Person.findById(req.params.id)
    .then(data => data.remove().then(() => res.send("delete was succesful")))
    .catch(error => res.send("The id doesnt exist"));
});

module.exports = router;
