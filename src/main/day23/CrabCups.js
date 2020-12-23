let input = [3, 6, 4, 2, 8, 9, 7, 1, 5];

const swapSections = (target) => {
  const removed = input.splice(1, 3);
  const indexOfTarget = input.indexOf(target);
  input.splice(indexOfTarget + 1, 0, ...removed);
};

const findDestinationCup = () => {
  let targetValue = input[0] - 1;
  let min = input.slice(4).reduce((a, b) => Math.min(a, b));
  let max = input.slice(4).reduce((a, b) => Math.max(a, b));
  while (true) {
    for (let i = 4; i < input.length; i++) {
      if (input[i] === targetValue) {
        return targetValue;
      }
    }
    targetValue--;
    if (targetValue < min) {
      targetValue = max;
    }
  }
};

const rotateLeft = () => {
  let elem = input.shift();
  input.push(elem);
};

for (let i = 0; i < 100; i++) {
  const target = findDestinationCup();
  swapSections(target);
  rotateLeft();
}

let whereIsOne = input.indexOf(1);
let piece = input.splice(0, whereIsOne);
input.push(...piece);
console.log(input.slice(1).join(""));
