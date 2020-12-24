const fs = require("fs");

const input = fs.readFileSync("input.txt", "utf8").split(/\n/g);

// Honestly do not know what the correct
// size of the grid should be. This was
// trial and error..
const Y_LIMIT = 1200;
const X_LIMIT = 600;

const coordinates = new Set();
for (const line of input) {
  // s and n have to be followed either by e or w
  const commands = [];
  const lineArray = line.split("");
  let i = 0;
  while (i < lineArray.length) {
    if (lineArray[i] === "e" || lineArray[i] === "w") {
      commands.push(lineArray[i]);
      i++;
    } else {
      // must be a combination of two
      commands.push([lineArray[i], lineArray[i + 1]].join(""));
      i += 2;
    }
  }
  // e  = x + 1
  // se = x + 0.5, y - 1
  // sw = x - 0.5, y - 1
  // w  = x - 1
  // nw = x - 0.5, y + 1
  // ne = x + 0.5, y + 1
  // reference 0,0, execute commands
  let starting = [X_LIMIT / 2, Y_LIMIT / 2];
  commands.forEach((command) => {
    switch (command) {
      case "e": {
        starting[0] = starting[0] + 1;
        break;
      }
      case "se": {
        starting[0] = starting[0] + 0.5;
        starting[1] = starting[1] - 1;
        break;
      }
      case "sw": {
        starting[0] = starting[0] - 0.5;
        starting[1] = starting[1] - 1;
        break;
      }
      case "w": {
        starting[0] = starting[0] - 1;
        break;
      }
      case "nw": {
        starting[0] = starting[0] - 0.5;
        starting[1] = starting[1] + 1;
        break;
      }
      case "ne": {
        starting[0] = starting[0] + 0.5;
        starting[1] = starting[1] + 1;
        break;
      }
    }
  });
  let coordString = starting.join(",");
  if (coordinates.has(coordString)) {
    // already flipped, remove
    coordinates.delete(coordString);
  } else {
    coordinates.add(coordString);
  }
}

// Coordinates is now the starting condition to the game of tiles.
const wickedArray = [];
for (let y = 0; y <= Y_LIMIT; y++) {
  wickedArray[y] = new Map();
  for (let x = 0; x <= X_LIMIT; x += 0.5) {
    const coordString = `${x},${y}`;
    if (coordinates.has(coordString)) {
      wickedArray[y].set(x, 1);
    } else {
      wickedArray[y].set(x, 0);
    }
  }
}

const shouldFlip = (oy, ox) => {
  let isBlackTile = wickedArray[oy].get(ox) === 1;
  let blackNeighbourTiles = 0;
  try {
    if (wickedArray[oy - 1].get(ox - 0.5)) {
      blackNeighbourTiles++;
    }
    if (wickedArray[oy - 1].get(ox + 0.5)) {
      blackNeighbourTiles++;
    }
    if (wickedArray[oy].get(ox - 1)) {
      blackNeighbourTiles++;
    }
    if (wickedArray[oy].get(ox + 1)) {
      blackNeighbourTiles++;
    }
    if (wickedArray[oy + 1].get(ox - 0.5)) {
      blackNeighbourTiles++;
    }
    if (wickedArray[oy + 1].get(ox + 0.5)) {
      blackNeighbourTiles++;
    }
  } catch (error) {
    // outside y boundaries, ignore
  }

  return isBlackTile
    ? blackNeighbourTiles === 0 || blackNeighbourTiles > 2
    : blackNeighbourTiles === 2;
};

const flipTiles = () => {
  // go through all the tiles and check game of tile rules
  let codes = new Set();
  for (let y = 0; y <= Y_LIMIT; y++) {
    for (let x = 0; x <= X_LIMIT; x += 0.5) {
      if (shouldFlip(y, x)) {
        codes.add(`${y}${x}`);
      }
    }
  }

  for (let y = 0; y <= Y_LIMIT; y++) {
    for (let x = 0; x <= X_LIMIT; x += 0.5) {
      if (codes.has(`${y}${x}`)) {
        wickedArray[y].set(x, wickedArray[y].get(x) === 0 ? 1 : 0);
      }
    }
  }
};

const countBlackTiles = () => {
  let count = 0;
  for (let y = 0; y <= Y_LIMIT; y++) {
    for (let x = 0; x <= X_LIMIT; x += 0.5) {
      if (wickedArray[y].get(x)) {
        count++;
      }
    }
  }
  return count;
};

const playGame = (rounds) => {
  for (let i = 0; i < rounds; i++) {
    flipTiles();
  }
};

playGame(100);
console.log(countBlackTiles());
