const fs = require("fs");

const input = fs.readFileSync("input.txt", "utf-8").split(/\n\n/);
const rules = input[0].split(/\n/);
const messages = input[1].split(/\n/);

let rulesDict = {};
rules.forEach((rule) => {
  const splitRule = rule.replace(/\"/g, "").split(/:/);
  rulesDict[splitRule[0].trim()] = splitRule[1].trim();
});

const mem = new Set();
while (true) {
  const replacable = {};
  Object.keys(rulesDict).forEach((key) => {
    const currentValue = rulesDict[key];
    if (!currentValue.match(/[0-9]/) && !mem.has(key)) {
      replacable[key] = ["a", "b"].includes(currentValue)
        ? currentValue
        : `(${currentValue.replace(/\s/g, "")})`;
      mem.add(key);
    }
  });
  if (!Object.keys(replacable).length) {
    break;
  }
  Object.keys(rulesDict).forEach((rKey) => {
    Object.keys(replacable).forEach((key) => {
      const valueRegexp = new RegExp(
        `(^${key}$)|(^${key} )|( ${key} )|( ${key}$)`,
        "g"
      );
      const match = valueRegexp.exec(rulesDict[rKey]);
      if (match !== null) {
        rulesDict[rKey] = rulesDict[rKey].replace(
          new RegExp(`^${key} `, "g"),
          `${replacable[key]} `
        );
        rulesDict[rKey] = rulesDict[rKey].replace(
          new RegExp(` ${key} `, "g"),
          ` ${replacable[key]} `
        );
        rulesDict[rKey] = rulesDict[rKey].replace(
          new RegExp(` ${key}$`, "g"),
          ` ${replacable[key]}`
        );
        rulesDict[rKey] = rulesDict[rKey].replace(
          new RegExp(`^${key}$`, "g"),
          `${replacable[key]}`
        );
      }
    });
  });
}

const rule8 = rulesDict["8"] + "+";
const rule11 = rulesDict["11"].split(/\s/g);
let c = 0;
messages.forEach((message) => {
  for (let i = 1; i < 10; i++) {
    const tester = new RegExp(`^(${rule8})(${rule11.join(`{${i}}`)}{${i}})$`);
    if (tester.test(message)) {
      c++;
    }
  }
});

console.log(c)