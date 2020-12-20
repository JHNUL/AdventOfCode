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

const regex = `^${rulesDict[0].replace(/\s/g, "")}$`;
const tester = new RegExp(regex);
let matched = 0;
for (let i = 0; i < messages.length; i++) {
  if (tester.test(messages[i])) {
    matched++;
  }
}

console.log(matched);
