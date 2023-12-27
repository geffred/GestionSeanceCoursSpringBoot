

const deleteBtns = document.querySelectorAll(".deleteBtn");
const deleteConfirmation = document.querySelector(".deleteConfirmation");
const delbtn = document.querySelector(".deleteConfirmation .b ")
deleteConfirmation.style.display ="none";

    deleteBtns.forEach(function(btn){
        btn.addEventListener("click",function(e){
            e.preventDefault();
            deleteConfirmation.style.display ="block";
            delbtn.setAttribute("href", this.getAttribute("href") )
        })
    })

