const fs = require("fs");

const input = fs
  .readFileSync("src/main/day21/input.txt", "utf-8")
  .split(/\n/g);

const intersection = (set1, set2) => {
  return new Set([...set1].filter((elem) => set2.has(elem)));
};

const elimination = (eliminateFrom, eliminateWhat) => {
  return new Set([...eliminateFrom].filter((elem) => !eliminateWhat.has(elem)));
};

const allergenDict = {};
const allergenRegex = new RegExp(/(\(.*\))$/);

for (const line of input) {
  const captured = allergenRegex.exec(line);
  const allergens = captured[1]
    .replace(/\(contains/, "")
    .replace(/\)/, "")
    .split(/,/g)
    .map((a) => a.trim());
  const ingredients = new Set(
    line.substring(0, line.indexOf("(")).trim().split(/\s/g)
  );
  for (const allergen of allergens) {
    if (!allergenDict[allergen]) {
      allergenDict[allergen] = ingredients;
    } else {
      allergenDict[allergen] = intersection(
        allergenDict[allergen],
        ingredients
      );
    }
  }
}

let ingredientsToRemove = new Set();
let changesMade = true;
const finalAllergens = {};
while (changesMade) {
  changesMade = false;
  // here one or more allergen has to be mapped to exactly one ingredient
  Object.entries(allergenDict).forEach(([allergen, ingredients]) => {
    if (ingredients.size === 1) {
      ingredients.forEach((i) => ingredientsToRemove.add(i));
      finalAllergens[allergen] = allergenDict[allergen];
      delete allergenDict[allergen];
      changesMade = true;
    }
  });
  if (changesMade) {
    Object.keys(allergenDict).forEach((allergen) => {
      allergenDict[allergen] = elimination(
        allergenDict[allergen],
        ingredientsToRemove
      );
    });
    ingredientsToRemove = new Set();
  }
}

const res = Object.entries(finalAllergens)
  .sort((e1, e2) => e1[0].localeCompare(e2[0]))
  .map((e) => [...e[1]][0])
  .join(",");

console.log(res);
