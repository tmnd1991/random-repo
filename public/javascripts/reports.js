/**
 * Created by tmnd on 27/03/17.
 */
var HIGHEST_COUNTRIES = "#highest-countries";
var LOWEST_COUNTRIES = "#lowest-countries";
var RUNWAY_TYPES = "#runway-types";
var RUNWAY_IDS = "#runway-ids";
$(function () {
    var hc = $(HIGHEST_COUNTRIES);
    searchCountriesAndCount("desc", hc);
    var lc = $(LOWEST_COUNTRIES);
    searchCountriesAndCount("asc", lc);
    var rt = $(RUNWAY_TYPES);
    searchCountriesAndSurfaceTypes(rt);
    var ri = $(RUNWAY_IDS);
    searchRunwaysId("desc", ri);
});


function searchCountriesAndCount(order, htmlElement) {
    $.get('/v1/countries?query=airports&order=' + order, function (data) {
        htmlElement.html(renderCountriesAndCount(data));
    }).fail(function () {
        var result = '<div class="row callout alert"><div class="small-12 columns"><h3>Server Error!</h3></div></div>';
        htmlElement.html(result);
    });
}

function renderCountriesAndCount(data) {
    var result = '<table><thead><tr><th>Country</th><th>Count</th></tr><tbody>';
    data.forEach(function (cAndCount) {
        result += '<tr><td><span class="hide-for-small-only">' + cAndCount.name + ' - </span>' + cAndCount.iso + '</td>' +
            '<td>' + cAndCount.count + '</td></tr>';
    });
    result += '</tbody></table>';
    return result;
}

function searchCountriesAndSurfaceTypes(htmlElement) {
    $.get('/v1/countries/runways', function (data) {
        htmlElement.html(renderCountriesAndSurfaceTypes(data));
    }).fail(function () {
        var result = '<div class="row callout alert"><div class="small-12 columns"><h3>Server Error!</h3></div></div>';
        htmlElement.html(result);
    });
}

function renderCountriesAndSurfaceTypes(data) {
    var result = '';
    data.forEach(function (cAndSurfaces) {
        result += '<div class="row callout"><div class="row">' +
            '<div class="small-12 columns country-name"><h4>' + cAndSurfaces.name + ' - ' + cAndSurfaces.iso + '</h4></div>' +
            '</div>';
        result += '<div class="row"><div class="small-12 columns"><ul>';
        cAndSurfaces.surfaceTypes.forEach(function (surface) {
            result += '<li>' + surface + '</li>'
        });
        result += '</ul></div></div>';
        result += '</div>';
    });
    return result;
}

function searchRunwaysId(order, htmlElement) {
    $.get('/v1/runways?query=le_ident&order=' + order, function (data) {
        htmlElement.html(renderRunwaysId(data));
    }).fail(function () {
        var result = '<div class="row callout alert"><div class="small-12 columns"><h3>Server Error!</h3></div></div>';
        htmlElement.html(result);
    });
}

function renderRunwaysId(data) {
    var result = '<table><thead><tr><th>Runway Id</th><th>Count</th></tr><tbody>';
    data.forEach(function (idCount) {
        result += '<tr><td>' + idCount.id + '</td><td>' + idCount.count + '</td></tr>';
    });
    result += '</tbody></table>';
    return result;
}