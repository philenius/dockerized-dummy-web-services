var express = require('express');
var router = express.Router();

let shutdownOngoing = false;
let remainingSec = 10;

router.get('/', function (req, res, next) {
  
  if (shutdownOngoing) {
    res.status(200).send(`shutdown already in process; shutting down in ${remainingSec} seconds`);
    return;
  }
  shutdownOngoing = true;

  console.log(`shutting down in ${remainingSec} seconds`);
  res.status(200).send(`shutting down in ${remainingSec} seconds`);
  
  setInterval(() => {
    remainingSec--;
    console.log(`shutting down in ${remainingSec} seconds`);
  }, 1000);

  setTimeout(() => {
    console.log('shutting down');
    process.exit();
  }, remainingSec * 1000);

});

module.exports = router;
