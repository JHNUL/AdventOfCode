const a = require("./adjacent");

let part1 = 0;
const fieldNames = Object.keys(a.fields);

// test number agains all fields to find out validity
// also collect excluded fields for part2
const testNumber = (num, classEntries) => {
  const excludedFields = [];
  for (const key of classEntries) {
    if (!a.fields[key](num)) {
      excludedFields.push(key);
    }
  }
  if (excludedFields.length === fieldNames.length) part1 += num;
  return excludedFields;
};

// filter out invalid tickets
const validTickets = a.tickets.filter((ticket) => {
  return !ticket.some(
    (field) => testNumber(field, fieldNames).length === fieldNames.length
  );
});

// available field should be the one not included in the set
const resolveAvailableField = (fieldSet, availableFieldNames) => {
  const availableFields = availableFieldNames.filter((c) => !fieldSet.has(c));
  if (availableFields.length > 1) {
    throw Error("More than one available field ", availableFields);
  }
  return availableFields[0];
};

const rowLen = validTickets[0].length;
let availableFieldNames = Object.keys(a.fields);
let resolvedFields = new Set();
let positions = {};
// iterate by column for as long as there are available fields left
while (availableFieldNames.length >= 1) {
  for (let i = 0; i < rowLen; i++) {
    if (resolvedFields.has(i)) {
      continue;
    }
    const excludedFieldsForColumn = new Set();
    for (let j = 0; j < validTickets.length; j++) {
      const num = validTickets[j][i];
      testNumber(num, availableFieldNames).forEach((c) =>
        excludedFieldsForColumn.add(c)
      );
    }
    if (excludedFieldsForColumn.size === availableFieldNames.length - 1) {
      // all but one field excluded
      // this must be our defined field for the column
      const field = resolveAvailableField(
        excludedFieldsForColumn,
        availableFieldNames
      );
      availableFieldNames = availableFieldNames.filter(
        (name) => name !== field
      );
      resolvedFields.add(i);
      positions[field] = i;
    }
  }
}

const part2res = Object.entries(positions)
  .filter(([k]) => k.indexOf("departure") === 0)
  .map(([, v]) => a.myTicket[v])
  .reduce((a, b) => a * b, 1);

console.log("Part1 ", part1);
console.log("Part2 ", part2res);
