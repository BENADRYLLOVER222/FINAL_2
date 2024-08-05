jQuery(function($) {
    $('#menuButton').click(function() {
        var submenu = $(this).next();
        var movingDiv = $('.page-content');
        if (submenu.css('display') === "block") {
            submenu.slideUp(300, function() {
                $('body').removeClass('menu-open'); // Удаляем класс при закрытии меню после анимации
            });
            movingDiv.animate({ top: '0px' }, 300); // Плавно возвращаем moving на исходное место (наверх)
        } else {
            submenu.slideDown(300);
            $('body').addClass('menu-open'); // Добавляем класс при открытии меню
            movingDiv.animate({ top: '0px' }, 300); // Плавно сдвигаем moving вниз
        }
    });
});