/**
 * Created by tmnd on 23/03/17.
 */
$('#querybox').keyup(function (e) {
    clearTimeout($.data(this, 'timer'));
    if (e.keyCode == 13)
        search();
    else
        $(this).data('timer', setTimeout(search, 500));
});
function search() {
    var existingString = $("#querybox").val().trim();
    var resultElement = $('#results');
    if (existingString.length > 0) {
        $.get('/v1/airports?country=' + existingString, function (data) {
            resultElement.html(renderResults(data));
        }).fail(function () {
            var result = '<div class="row"><div class="large-12 columns"><h3>Cannot find any country for: ' +
                existingString + '</h3></div></div>';
            resultElement.html(result);
        });
    }
}

function renderResults(data) {
    var result = '<div class="row"><div class="large-12 columns"><h3>' + data.name + '</h3></div></div>' +
        '<div class="row"><div class="large-12 columns">';
    if (data.airports.length > 0) {
        result += '<ul>';
        data.airports.forEach(function (airport) {
            result += '<li>' + airport.name + '</li>';
            if (airport.runways.length > 0) {
                result += '<ul>';
                airport.runways.forEach(function (runway) {
                    result += '<li> airport identifier: ' + runway.airport_ident +
                        ' length (ft): ' + runway.length_ft + ' surface: ' + runway.surface + '</li>';
                });
                result += '</ul>';
            }
        });
        result += '</ul>';
    } else {
        result += "There isn't any airport in " + data.name + "!";
    }
    result += '</div></div>';
    return result;
}