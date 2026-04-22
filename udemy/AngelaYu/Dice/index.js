var randomNumber1 = Math.floor(Math.random()*6)+1;
var imageNumber1 = "images/dice" + randomNumber1 + ".png";
document.querySelectorAll("img")[0].setAttribute("src", imageNumber1);

var randomNumber2 = Math.floor(Math.random()*6)+1;
var imageNumber2 = "images/dice" + randomNumber2 + ".png";
document.querySelectorAll("img")[1].setAttribute("src", imageNumber2);


if(randomNumber1 > randomNumber2){
    document.querySelector("h1").innerHTML= "Player One Wins!";
} else if(randomNumber2 > randomNumber1){
    document.querySelector("h1").innerHTML= "Player Two Wins!";
} else {
    document.querySelector("h1").innerHTML = "Draw! Refresh to Play Again";
}