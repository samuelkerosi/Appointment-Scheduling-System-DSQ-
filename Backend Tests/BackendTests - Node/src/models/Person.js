const mongoose = require("mongoose");
const Schema = mongoose.Schema;

const Person = new Schema({
    fname: {
        type: String,
        require: true

    },
    lname:{
        type: String,
        require: true
    },
    checkInTime:{
        type: Date,
        default: Date.now
    }
});

                                    //schemaName and schema itself
module.exports = Schedule = mongoose.model("checkInTime", Person)

