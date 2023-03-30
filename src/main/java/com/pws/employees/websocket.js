var socket = new WebSocket("ws://localhost:8080/my-websocket");

socket.onopen = function() {
  console.log("WebSocket connection established");
};

socket.onmessage = function(event) {
  console.log("Received message: " + event.data);
};

socket.onclose = function(event) {
  console.log("WebSocket connection closed with code " + event.code);
};
