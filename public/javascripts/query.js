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
    var result = '<div class="row"><div class="large-12 columns"><h3>' + data.name + ' - ' + data.iso + '</h3></div></div>' +
        '<div class="row"><div class="large-12 columns">';
    if (data.airports.length > 0) {
        data.airports.forEach(function (airport) {
            result += '<div class = "row callout">';
            result += '<h4>' + airport.name + '</h4>';
            if (airport.runways.length > 0) {
                result += '<table><thead><tr>' +
                    '<th>Identifier</th>' +
                    '<th>Surface</th>' +
                    '<th>Length (ft)</th>' +
                    '<th class="hide-for-small-only">Width(ft)</th>' +
                    '<th class="hide-for-small-only">Latitude °</th>' +
                    '<th class="hide-for-small-only">Longitude °</th>' +
                    '</tr></thead><tbody>';
                airport.runways.forEach(function (runway) {
                    result += '<tr><td>' + runway.airport_ident + '</td><td>' + runway.surface + '</td>' +
                        '<td>' + getOrElse(runway.length_ft, 'N.A.') + '</td>' +
                        '<td class="hide-for-small-only">' + getOrElse(runway.width_ft, 'N.A.') + '</td>' +
                        '<td class="hide-for-small-only">' + getOrElse(runway.latitude_deg, 'N.A.') + '</td>' +
                        '<td class="hide-for-small-only">' + getOrElse(runway.longitude_deg, 'N.A.') + '</td>';
                });
                result += '</tbody></table>';
            }
            result += '</div>';
        });
    } else {
        result += "There isn't any airport in " + data.name + "!";
    }
    result += '</div></div>';
    return result;
}

function getOrElse(x, fallback) {
    if (x === undefined) {
        return fallback;
    } else {
        return x;
    }
}