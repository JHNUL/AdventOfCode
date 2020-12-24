const input = [3, 6, 4, 2, 8, 9, 7, 1, 5];
for (let i = 9; i < 1000000; i++) {
  input.push(i + 1);
}
// we only need to know to which number any given
// number is pointing -> save in another array like
// at index 3 is number 6, at index 6 is number 4 etc
const placing = [];
for (let i = 0; i < input.length - 1; i++) {
  placing[input[i]] = input[i + 1];
}
placing[input[input.length - 1]] = input[0];

// since the input not read from any file, just hardcode these here
const min = 1; 
const max = 1000000;

const playRound = (rounds) => {
  let i = 0;
  let startingValue = input[0];
  while (i < rounds) {
    // find out destination
    let destination = startingValue - 1 < min ? max : startingValue - 1;
    const first = placing[startingValue];
    const second = placing[first];
    const third = placing[second];
    while (true) {
      if ([first, second, third].includes(destination)) {
        destination = destination - 1 < min ? max : destination - 1;
      } else {
        break;
      }
    }

    // save where current is pointing to
    let temp = placing[startingValue];

    // change current pointing to where last selected pointed to
    placing[startingValue] = placing[third];

    // change last selected to point where destination pointed to
    placing[third] = placing[destination];

    // change destination to point to first selected
    placing[destination] = temp;

    // resolve next starting value
    startingValue = placing[startingValue];
    i++;
  }
};

playRound(10e6);
let first = placing[1];
let second = placing[first];

console.log(first * second);
