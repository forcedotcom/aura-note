({
	createNote: function(component) {
		var event = $L.get("e.lumennote:openNote")
		event.setParams({mode : "new"});
		event.fire();
	},
	
	noteAdded : function(component, event){
		var notesValue = component.getValue("m.notes");
		
		var notes = notesValue.unwrap();
		notes.splice(0,1,event.getParam("note"));
		notesValue.setValue(notes);
	}
})