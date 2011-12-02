({
	createNote: function(component) {
		var event = $L.get("e.lumennote:openNote")
		event.setParams({mode : "new"});
		event.fire();
	}
})