$(document).ready(function () {
    //var project;
    // Get the timeslots associated with the project
    // Create the add TimeSlot form
    renderTimeSlots(PROJECT_ID);
    buildTimeSlotForm(IS_SUPERVISOR);

});

var renderTimeSlots = function(projectId) {
    var uri = "/presentation/get-times";
    $.ajax({
        method: "GET",
        url: uri,
        contentType: 'application/json',
        data: {id: projectId}
    }).then(function(response) {
        if(response.length > 0) {
            var timeSlotsDiv = $("#time-slots");
            timeSlotsDiv.empty();
            var title = $(document.createElement("h2")).text("Available Times");
            timeSlotsDiv.append(title);
            var form = $(document.createElement("form")).attr("id", "time-select");
            response.forEach(function(time) {
               renderTimeSlot(form, time);
            });
            var submit = $(document.createElement("input"))
                .attr("type", "submit")
                .attr("value", "Update")
                .attr("id", "time-select-submit")
                .click(timeSelectSubmit);
            form.append(submit);
            timeSlotsDiv.append(form);
        }
    })
}

var renderTimeSlot = function(div, timeSlot) {
    if(timeSlot.startMinute == 0) timeSlot.startMinute = "00";
    var timeSlotElem = $(document.createElement("input"))
        .attr("type", "checkbox")
        .attr("id", "timeSlot_"+timeSlot.id)
        .addClass("times")
        .prop("checked", timeSlot.selected);
    var timeSlotLabel = $(document.createElement("label"))
        .attr("for", "timeSlot_"+timeSlot.id)
        .text(timeSlot.day + " at " + timeSlot.startHour + ":" + timeSlot.startMinute);
    var br = $(document.createElement("br"));
    div.append(timeSlotElem).append(timeSlotLabel).append(br);
}

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
        id: PROJECT_ID,
        day: inputs[0].value,
        hour: inputs[1].value,
        minute: inputs[2].value
    }
    var uri = "/presentation/new-time";
    $.ajax({
        method: "POST",
        url: uri,
        contentType: 'application/json',
        data: params
    }).then(function(data){
        renderTimeSlots(PROJECT_ID);
    });
}
var timeSelectSubmit = function (event) {
    event.preventDefault();
    var inputs = $(".times");
    var params = {
        id: PROJECT_ID,
        timeSlots: []
    };
    inputs.each(function (input) {
        var time = {
            id : $(inputs[input]).attr("id").substr(9),
            selected: $(inputs[input]).is(":checked")
        };
        params.timeSlots.push(time);
    });
    var uri = "/presentation/update";
    $.ajax({
        method: "POST",
        url: uri,
        contentType: 'application/json',
        data: JSON.stringify(params)
    });
}

