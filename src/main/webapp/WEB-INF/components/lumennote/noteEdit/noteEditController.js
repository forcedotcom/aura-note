({
	cancel: function(component) {
		var note = component.getValue("v.note");
		note.getValue("title").rollback();
		note.getValue("body").rollback();
		
		var event = $L.get("e.lumennote:openNote")
		event.setParams({
			mode : "view",
			note : note
		});
		event.fire();
	},
	
	save : function(component){
		var action = component.get("c.saveNote");
		var note = component.getValue("v.note");
		note.getValue("title").commit();
		note.getValue("body").commit();
		
		action.setParams({
			title : note.get("title"),
			body : note.get("body"),
			id : note.get("id")
		});
		
		action.setCallback(this, function(a){
			var event = $L.get("e.lumennote:noteAdded");
			event.setParams({note : a.getReturnValue()});
			event.fire();
		});
		
		this.runAfter(action);
	}
})