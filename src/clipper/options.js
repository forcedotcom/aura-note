// Saves options to localStorage.
function save() {
  var server = document.getElementById("server");
  localStorage["server"] = server.value;

  // Update status to let user know options were saved.
  var status = document.getElementById("status");
  status.innerHTML = "Options Saved.";
  setTimeout(function() {
    status.innerHTML = "";
  }, 750);
}

// Restores select box state to saved value from localStorage.
function restore() {
  var server = localStorage["server"];
  if(!server){
    server = "http://localhost:8080/";
  }
  document.getElementById("server").value = server;
}

// Initialization
document.addEventListener('DOMContentLoaded', function () {
  document.querySelector('button').addEventListener('click', save);
  restore();
})