const fs = require("fs");

const input = fs
  .readFileSync("src/main/day21/testinput.txt", "utf-8")
  .split(/\n/g);

const intersection = (set1, set2) => {
  return new Set([...set1].filter((elem) => set2.has(elem)));
};

const elimination = (eliminateFrom, eliminateWhat) => {
  return new Set([...eliminateFrom].filter((elem) => !eliminateWhat.has(elem)));
};

let eliminatedIngredients = new Set();
const allergenDict = {};
const allergenRegex = new RegExp(/(\(.*\))$/);
const allIngredientsDict = {};

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
  ingredients.forEach((i) => {
    eliminatedIngredients.add(i);
    if (!allIngredientsDict[i]) {
      allIngredientsDict[i] = 1;
    } else {
      allIngredientsDict[i] = allIngredientsDict[i] + 1;
    }
  });
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

Object.values(allergenDict).forEach((ingredientSet) => {
  eliminatedIngredients = elimination(eliminatedIngredients, ingredientSet);
});

let count = 0;
eliminatedIngredients.forEach((eIng) => {
  count += allIngredientsDict[eIng];
});

console.log(count);
