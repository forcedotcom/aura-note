({
	createNote: function(compoent, event, helper) {
		var action = compoent.get("c.createNoteAction");
		
		this.runAfter(action);
	}
})