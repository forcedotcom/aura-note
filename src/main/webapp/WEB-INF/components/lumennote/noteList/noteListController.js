({
	createNote: function(component, event, helper) {
		var action = component.get("c.createNoteAction");
		action.setParams({
			title : "This is a title",
			body : "This is a body <b>(bold)</b>"
		});
		
		this.runAfter(action);
	}
})