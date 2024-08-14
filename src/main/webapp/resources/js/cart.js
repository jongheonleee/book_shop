$(document).ready(function() {
    $('.quantity-decrease').on('click', function() {
        let $input = $(this).siblings('.quantity-input');
        let currentValue = parseInt($input.val());
        if (currentValue > 1) {
            $input.val(currentValue - 1);
        }
    });

    $('.quantity-increase').on('click', function() {
        let $input = $(this).siblings('.quantity-input');
        let currentValue = parseInt($input.val());
        $input.val(currentValue + 1);
    });
})