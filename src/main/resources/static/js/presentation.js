$(document).ready(function () {
    console.log("OVER HERE");
    //var project;
    // Get the timeslots associated with the project
    // Create the add TimeSlot form
    buildTimeSlotForm(true);

});

var buildTimeSlotForm = function(isProf) {
    if(!isProf) return;
    var allowedHours = ["8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18"];
    var allowedMinutes = ["00", "30"];
    var allowedDay = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday"];
    var hourDropDown = buildSelect(allowedHours);
    var minuteDropDown = buildSelect(allowedMinutes);
    var dayDropDown = buildSelect(allowedDay);

    var form = $(document.createElement("form"));
    var hourLabel = $(document.createElement('span')).text("Hour");
    var minuteLabel = $(document.createElement('span')).text("Minute");
    var dayLabel = $(document.createElement('span')).text("Day");

    var submit = $(document.createElement("input"))
                    .attr("type", "submit")
                    .attr("value", "Create")
                    .attr("id", "time-slot-submit")
                    .click(timeSlotSubmit);
    var dayDiv = $(document.createElement("div")).append(dayLabel).append(dayDropDown);
    var hourDiv = $(document.createElement("div")).append(hourLabel).append(hourDropDown);
    var minDiv = $(document.createElement("div")).append(minuteLabel).append(minuteDropDown);
    form.append(dayDiv)
        .append(hourDiv)
        .append(minDiv)
        .append(submit);
    $("#new-time-form").append(form);
}


var buildSelect = function(array) {
    var dropDown = $(document.createElement("select")).attr("class", "new-slot-input");
    array.forEach(function(value) {
        var option = document.createElement("option");
        option.setAttribute("value", value);
        option.innerHTML = value;
        dropDown.append(option);
    });
    return dropDown;
}

var timeSlotSubmit = function (event) {
    event.preventDefault();
    var inputs = $(".new-slot-input");
    var params = {
        day: encodeURIComponent(inputs[0].value),
        hour: encodeURIComponent(inputs[1].value),
        minute: encodeURIComponent(inputs[2].value)
    }
    var uri = "/project/1/presentation/new-time";
    $.ajax({
        method: "POST",
        uri: uri,
        contentType: 'application/json',
        data: JSON.stringify(params)
    }).then(function(data){
        console.log(data);
    });


}

