({
	openNote: function(component, event, helper) {
		var note = event.getParam("note");
		var mode = event.getParam("mode");
		var sort = event.getParam("sort");
		helper.open(component, note, mode, sort);
	}
})