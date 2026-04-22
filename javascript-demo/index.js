// Comment
// console.log('Hello World');

// let is assigning variable
// Meaningful variable names that are not let, var, if, etc.
// Cannot start with number, cannot have space, hyphen

let firstName = 'Ramyan';
let lastName = 'Chelva';

console.log(firstName);

let interestRate = 0.3;
interestRate = 1;

// Changed to 1
console.log(interestRate);

// const keeps constant -> Use if number won't change

const rates = 1;
console.log(rates);

// Primitives are strings, number (no float, int, just number), boolean, undefined

let middleName = undefined

// null is used to clear value of variable

// Dynamic typing

// Object has key value pairs

let person = {
    name: 'Ramyan',
    age: 22
};

console.log(person)

// Dot notation

person.name = 'Ramswag';
console.log(person)

// Bracket notation

person['name'] = 'Ram';
console.log(person)

// Another way to use bracket notation

let selection = 'name';
person[selection] = 'Ramyan';
console.log(person);

// Array, 0 indexed

let selectedColors = ['red', 'blue'];

// Can dynamically increase size of array
selectedColors[2] = 'green';

console.log(selectedColors[2]);

selectedColors[3] = 1;
console.log(selectedColors.length);

// Can have parameters
function greet(name, age) {
    if(age>=18){
        console.log('Hello ' + name + '. You are ' + age + ' years old');
    } else {
        console.log('You are too young to enter');
    }
}

// Call using arguments
greet(firstName, person.age);

function square(number){
    return number*number;
}

let squareOfTwo = square(2);
console.log(squareOfTwo);

// For loops

let hobbies = ['Video games', 'Reading books', 'Playing chess'];

for(let i = 0; i<hobbies.length; i++){
    console.log(hobbies[i]);
}

console.log(hobbies.includes('Video games'));
console.log(hobbies.includes('Watching movies'));

const prompt = require("prompt-sync")()

// User input
const v = prompt("Type something: ")
console.log(v);

var x = 10;
var y = "10";

if(x === y){
    console.log("Yes");
} else {
    console.log("No");
}

function fizzBuzz(target){
    var num = 1;
    var answer = [];
    while(num <= target){
        if (num % 3 === 0 && num % 5 !== 0){
            answer.push("fizz");
        } else if(num % 5 === 0 && num % 3 !== 0){
            answer.push("buzz");
        } else if (num % 3 === 0 && num % 5 === 0){
            answer.push("fizzbuzz");
        } else {
            answer.push(num);
        }

        num++;
    }

    return answer;
    
}

console.log(fizzBuzz(15));