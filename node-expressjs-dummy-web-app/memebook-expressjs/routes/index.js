const express = require('express');
const router = express.Router();

const randomLikes = () => {
  return Math.floor(Math.random() * 100);
}

/* GET home page. */
router.get('/', function (req, res, next) {

  const memebookVariant = process.env.MEMEBOOK_VARIANT;
  const standardWidth = '100%';
  const dogs = [
    { src: 'https://i.imgur.com/gIaWpqq.mp4', description: 'Guilty dogs', likes: randomLikes(), width: standardWidth },
    { src: 'https://i.imgur.com/jxWYgnq.mp4', description: 'Store owners dog does this to all customers', likes: randomLikes(), width: standardWidth },
    { src: 'https://i.imgur.com/DZqeyOO.mp4', description: 'The moment when he figured out we were going to the vet and not the dog park', likes: randomLikes(), width: standardWidth },
    { src: 'https://i.imgur.com/9O1rLtw.mp4', description: 'The coolest dog in the world has been spotted on a train', likes: randomLikes(), width: standardWidth },
    { src: 'https://i.imgur.com/Bw3mIJF.mp4', description: 'Ever wonder what a baby emu looks like while playing with a dog?', likes: randomLikes(), width: standardWidth },
    { src: 'https://i.imgur.com/VKCrLAF.mp4', description: 'The most helpful dog in the world', likes: randomLikes(), width: standardWidth },
  ];
  const cats = [
    { src: 'https://i.imgur.com/jhcN1Ke.mp4', description: 'Ingenious cat toy', likes: randomLikes(), width: standardWidth },
    { src: 'https://i.imgur.com/7nLm7si.mp4', description: 'Meet the cat who knows how to protect its cash', likes: randomLikes(), width: standardWidth },
    { src: 'https://i.imgur.com/JUoTWhO.mp4', description: 'National cat day', likes: randomLikes(), width: standardWidth },
    { src: 'https://i.imgur.com/QX7L70g.mp4', description: 'Big cats are just little cats that can kill you faster', likes: randomLikes(), width: standardWidth },
    { src: 'https://i.imgur.com/EuXVl0Z.mp4', description: 'Cat brings flowers to neighbour', likes: randomLikes(), width: standardWidth },
    { src: 'https://i.imgur.com/PQsnt73.mp4', description: 'The cat bell', likes: randomLikes(), width: '180%' },
  ];

  if (memebookVariant && memebookVariant.toLowerCase() === 'cats') {
    res.render('index', { title: 'CATBOOK', memes: cats });
    return;
  }

  res.render('index', { title: 'DOGBOOK', memes: dogs });

});

module.exports = router;
