class TypeWriter {
    constructor(txtElement, words, wait = 3000) {
        this.txtElement = txtElement;
        this.words = words;
        this.txt = '';
        //index of the the current string being typedout
        this.wordIndex = 0;
        //this.wait must be a base 10 interger
        this.wait = parseInt(wait, 10);
        //method type()
        this.type();
        // Boolean if the word is currently deleting
        this.isDeleting = false;
    }


    type() {
        //current index of words
        const current = this.wordIndex % this.words.length;
        //get full text of current word
        const fullTxt = this.words[current];

        // check if deleting
        if (this.isDeleting){
            //remove character
            this.txt = fullTxt.substring(0, this.txt.length - 1);
        } else {
            //add a charaacter
            this.txt = fullTxt.substring(0, this.txt.length + 1);
        }




        // insert txt into element
        this.txtElement.innerHTML = `<span class="txt">${this.txt}</span>`;

        //type speed for when it is typing, deleting and pausing after deletion

        let typeSpeed = 300;

        //select pencil icon for writting animation
        const typingElement = document.querySelector('.fas');

        if (this.isDeleting){
            typeSpeed /= 4;
        }

        if(this.isDeleting){
            typingElement.className = "fas fa-pencil-alt erasing-animation";
        }else{
            typingElement.className = "fas fa-pencil-alt writing-animation";
        }

        // if word is complete
        if(!this.isDeleting && this.txt === fullTxt){
            // make pause at end
            typeSpeed = this.wait;
            //set delete to true
            this.isDeleting = true;
            //will stop the pencil animation after word completion
            typingElement.className = "fas fa-pencil-alt";


        } else if (this.isDeleting && this.txt === ''){
            this.isDeleting = false;
            //move to the next word in the HTML property
            this.wordIndex++;
            // pause time before the word start typing
            typeSpeed = 1000;

        }

        setTimeout(() => this.type(), typeSpeed)
    }
}


//Init On DOM Load
document.addEventListener('DOMContentLoaded', init);

//Init App
function init() {
    const txtElement = document.querySelector('.txt-type');
    // const words = JSON.parse(txtElement.getAttribute('data-words'));
    const words = ['Website?','Web App', 'Problem Solver?'];


    // const wait = txtElement.getAttribute('data-wait');
    const wait = 2500;

    new TypeWriter(txtElement, words, wait);
}