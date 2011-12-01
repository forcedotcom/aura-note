({
	edit: function(component) {
		var note = component.getValue("v.note");
		
		var event = $L.get("e.lumennote:openNote")
		event.setParams({
			mode : "edit",
			note : note
		});
		event.fire();
	}
})