        document.addEventListener('DOMContentLoaded', function () {
            // Находим все элементы с классом .quantityInput
            var quantityInputs = document.querySelectorAll('.quantityInput');
            // Находим все элементы с классом .price и .totalSpan
            var priceSpans = document.querySelectorAll('.price span');
            var totalSpans = document.querySelectorAll('.totalSpan');
            var subtotalSpan = document.getElementById('subtotalSpan');

            // Обновляем значение общей суммы
            function updateSubtotal() {
                var subtotal = 0;
                totalSpans.forEach(function (totalSpan) {
                    subtotal += parseFloat(totalSpan.textContent);
                });
                subtotalSpan.textContent = subtotal.toFixed(2);
            }

            // Функция для обновления значения totalSpan
            function updateTotal(input, priceSpan, totalSpan) {
                var priceValue = parseFloat(priceSpan.textContent);
                var quantityValue = parseInt(input.value);

                if (quantityValue < 0) {
                    input.value = 0;
                    quantityValue = 0;
                }

                var totalValue = priceValue * quantityValue;
                totalSpan.textContent = totalValue.toFixed(2);
                updateSubtotal();
            }

            // Слушаем событие input для каждого элемента с классом quantityInput
            quantityInputs.forEach(function (input, index) {
                var priceSpan = priceSpans[index];
                var totalSpan = totalSpans[index];
                input.addEventListener('input', function () {
                    updateTotal(input, priceSpan, totalSpan);
                });
                // Вызываем updateTotal для каждого товара при загрузке страницы
                updateTotal(input, priceSpan, totalSpan);
            });
        });

document.addEventListener('DOMContentLoaded', function() {
  var addButton = document.getElementById('addNoteButton');
  var noteTextarea = document.getElementById('noteTextarea');

  addButton.addEventListener('click', function(event) {
    event.preventDefault(); // Предотвращаем действие по умолчанию (отправку формы)
    noteTextarea.style.display = 'block'; // Отображаем textarea при нажатии на кнопку
    addButton.textContent = 'Special instructions for seller'; // Изменяем текст кнопки
  });
});