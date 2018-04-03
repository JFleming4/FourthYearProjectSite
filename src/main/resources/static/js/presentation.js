$(document).ready(function () {
    //var project;
    // Get the timeslots associated with the project
    // Create the add TimeSlot form
    renderTimeSlots($("#projectId").text());
    document.querySelector("#time-slot-submit").addEventListener("click", timeSlotSubmit);
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
            timeSlotsDiv.append(form);
        }
    })
}

var renderTimeSlot = function(div, timeSlot) {
    if(timeSlot.startMinute == 0) timeSlot.startMinute = "00";
    var timeSlotElem = $(document.createElement("input"))
        .attr("type", "checkbox")
        .attr("id", "timeSlot_"+timeSlot.id);
    var timeSlotLabel = $(document.createElement("label"))
        .attr("for", "timeSlot_"+timeSlot.id)
        .text(timeSlot.day + " at " + timeSlot.startHour + ":" + timeSlot.startMinute);
    var br = $(document.createElement("br"));
    div.append(timeSlotElem).append(timeSlotLabel).append(br);
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
        id: $("#projectId").text(),
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
        renderTimeSlots($("#projectId").text());
    });


}

