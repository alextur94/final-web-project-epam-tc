(function() {
    'use strict';
    window.addEventListener('load', function() {
        // fetch all the forms we want to apply custom style
        var inputs = document.getElementsByClassName('form-control')

        // loop over each input and watch blur event
        var validation = Array.prototype.filter.call(inputs, function(input) {

            input.addEventListener('blur', function(event) {
                // reset
                input.classList.remove('is-invalid')
                input.classList.remove('is-valid')

                if (input.checkValidity() === false) {
                    input.classList.add('is-invalid')
                }
                else {
                    input.classList.add('is-valid')
                }
            }, false);
        });
    }, false);
})()