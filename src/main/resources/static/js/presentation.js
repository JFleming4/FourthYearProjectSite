$(document).ready(function() {
    $('input[type="checkbox"]').on('change', function() {
        $('input[type="checkbox"]').not(this).prop('checked', false);
    });
    $(".delete").click(function (event){
        var string = 'time_';
        string += $(this).attr('id');
        document.forms[string].submit()
    })
})