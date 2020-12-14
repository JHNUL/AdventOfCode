const fs = require("fs");

const input = fs
  .readFileSync("input.txt", "utf-8")
  .split(/,/g)
  .map((i) => Number(i));

const inputMap = new Map();
for (let i = 0; i < input.length; i++) {
  if (!Number.isNaN(input[i])) {
    inputMap.set(input[i], i);
  }
}

const modInput = input.filter((i) => !Number.isNaN(i));

// Don't even need this bullshit
// const getPrimeFactors = (num) => {
//   const primeFactorsMap = new Map();
//   for (let i = 2; i <= num; i++) {
//     while (num % i == 0) {
//       let pow = primeFactorsMap.get(i);
//       if (!Number.isInteger(pow)) {
//         pow = 0;
//       }
//       primeFactorsMap.set(i, pow + 1);
//       num = num / i;
//     }
//   }
//   return primeFactorsMap;
// };

// const LCM = (num1, num2) => {
//   if (num1 === 0 || num2 === 0) {
//     return 0;
//   }
//   const num1Factors = getPrimeFactors(num1);
//   const num2Factors = getPrimeFactors(num2);
//   const union = new Set(num1Factors.keys());
//   num2Factors.forEach((v, k) => union.add(k));

//   let lcm = 1;
//   for (const int of union) {
//     const factorsInNum1 = num1Factors.has(int) ? num1Factors.get(int) : 0;
//     const factorsInNum2 = num2Factors.has(int) ? num2Factors.get(int) : 0;
//     lcm *= Math.pow(int, Math.max(factorsInNum1, factorsInNum2));
//   }
//   return lcm;
// };

let timestamp = 1;
let step = 1;
let curr = 0;
// modInput includes only buses
while (curr < modInput.length) {
  // Idea is to go through each bus at a time and check the offset condition
  // for the current bus. Once that is resolved, increment the step with the
  // LCM of the step and the current bus (since all buses are primes it is
  // conveniently just the product of the two)
  if ((timestamp + inputMap.get(modInput[curr])) % modInput[curr] === 0) {
    step = step * modInput[curr];
    curr++;
  }
  timestamp += step;
}
console.log(timestamp - step)
