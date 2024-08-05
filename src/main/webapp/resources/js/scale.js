document.addEventListener('DOMContentLoaded', function() {
    var mainImage = document.querySelector('.mainImage');
    mainImage.classList.remove('scale');
});

function toggleScale(element) {
    element.classList.toggle('scale');
}