$(document).ready(function() {
    $('#startDate, #stopDate').datetimepicker({
        format: 'Y-m-d H:i'
    });
});

$(function () {
    $("#showReport").on("click", function () {
        $reportsType = $("#reportsType").val()
        $startDate = $("#startDate").val()
        $stopDate = $("#stoptDate").val()

        $.get(baseUrl + '/Reportes/searchReports', {
            type: $reportsType,
            startDate : $startDate,
            stopDate : $stopDate
        }, function (data) {

        });

    })
})

$('.startDate').datetimepicker({
    format: 'Y-m-d'
});

$('.endDate').datetimepicker({
    format: 'Y-m-d'
});