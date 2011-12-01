({
	createNote: function(component, event, helper) {
		var action = component.get("c.createNoteAction");
		
		this.runAfter(action);
	}
})