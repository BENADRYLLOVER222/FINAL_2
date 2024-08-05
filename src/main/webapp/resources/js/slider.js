var swiper = new Swiper('.image-slider', {
  loop: true,
  autoplay: {
    delay: 3000,
    disableOnInteraction: false
  },
  speed: 500,
  effect: 'slide',
  touchRatio: 1, // Разрешаем свайп на всех разрешениях
  slidesPerView: 1,
  grabCursor: true,
  spaceBetween: 0,
  navigation: {
    nextEl: '.swiper-button-next',
    prevEl: '.swiper-button-prev'
  },
  breakpoints: {
    768: {
      // Опции для разрешения шириной >= 768px
      touchRatio: 0, // Отключаем свайп на разрешениях 768px и выше
      navigation: {
      }
    },
  }
});

$(document).ready(function () {
  // При наведении курсора на слайдер показываем кнопки навигации
  $('.image-slider').on('mouseenter', function () {
    $('.swiper-button-prev, .swiper-button-next').fadeIn();
  });

  // При уходе курсора с слайдера скрываем кнопки навигации
  $('.image-slider').on('mouseleave', function () {
    $('.swiper-button-prev, .swiper-button-next').fadeOut();
  });
});