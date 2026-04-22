function giveClosure() {
    let a = 10;
    return function() {
        a++;
        return a;
    };
}

const counter = giveClosure();

console.log(counter());