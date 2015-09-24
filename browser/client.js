$(document).ready(function() {
	ws = new WebSocket("ws://jbosswildfly-gdcgamification.rhcloud.com:8000/wsrest/echo");

	ws.onmessage = function(event) {
		$("#messages").append("<p>" + event.data + "</p>");
	};
	
	ws.onclose = function() {
		console.log("Socket closed");
	};
	
	ws.onopen = function() {
		console.log("Connected");
		ws.send("Username:" + "Test2");
	};
	
	// $("#new-message").bind("submit", function(event) {
	// 	event.preventDefault();
	// 	ws.send($("#message-text").val());
	// 	$("#message-text").val("");
	// });
});
