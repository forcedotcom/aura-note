({
	openNote: function(component, event, handler) {
		var note = event.getParam("note");

		var noteView = $L.services.component.newLocalComponent({
        	componentDef: {
        		descriptor: "markup://lumennote:note"
        	},
        	
        	attributes: {
        		values: {
        			note: note
        		}
        	}
        })
		
		var content = component.find("content");
		var body = content.getValue("v.body");
		body.setValue(noteView);
	}
})