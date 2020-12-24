const fs = require("fs");

const input = fs.readFileSync("input.txt", "utf8").split(/\n/g);

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
  let starting = [0, 0];
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

console.log(coordinates.size);
