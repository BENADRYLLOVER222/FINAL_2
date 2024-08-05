document.getElementById("sizeButton").onclick = function() {
    document.getElementById("sizeDropdown").classList.toggle("show");
}

function setSize(size) {
    document.getElementById("sizeButton").innerText = size;
    document.getElementById("sizeDropdown").classList.remove("show");
}

window.onclick = function(event) {
    if (!event.target.matches('.dropbtn')) {
        var dropdowns = document.getElementsByClassName("dropdown-content");
        var i;
        for (i = 0; i < dropdowns.length; i++) {
            var openDropdown = dropdowns[i];
            if (openDropdown.classList.contains('show')) {
                openDropdown.classList.remove('show');
            }
        }
    }
}