({
	openNote: function(component, event) {
		var note = event.getParam("note");
		var mode = event.getParam("mode");
		if(mode === "new"){
			note = $L.expressionService.create(null, {title : "", body : ""})
		}

		var noteView = $L.services.component.newLocalComponent({
        	componentDef: {
        		descriptor: "markup://lumennote:note"
        	},
        	
        	attributes: {
        		values: {
        			note : note,
        			mode : mode
        		}
        	}
        });
        
		var content = component.find("notes");
		var body = content.getValue("v.body");
		//body.destroy();
		body.setValue(noteView);
	}
		/*
		alert("new!");
		var action = component.get("c.createNoteAction");
		action.setParams({
			title : "This is a title",
			body : "This is a body <b>(bold)</b>"
		});
		
		this.runAfter(action);
		*/
})