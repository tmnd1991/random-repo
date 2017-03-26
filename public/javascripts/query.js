/**
 * Created by tmnd on 23/03/17.
 */
$('#querybox').keyup(function(e) {
    clearTimeout($.data(this, 'timer'));
    if (e.keyCode == 13)
        search(true);
    else
        $(this).data('timer', setTimeout(search, 500));
});
function search(force) {
    var existingString = $("#querybox").val();
    if (!force) return; //wasn't enter
    $.get('/Tracker/Search/' + existingString, function(data) {
        $('div#results').html(data);
        $('#results').show();
    });
}