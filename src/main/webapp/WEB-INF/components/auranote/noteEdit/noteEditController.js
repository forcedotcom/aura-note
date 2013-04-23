({
	cancel: function(component) {
		var note = component.getValue("v.note");

        // Revert title/body text
        note.put("title", component.getValue("m.origTitle").getValue());
        note.put("body", component.getValue("m.origBody").getValue());

		var event = $A.get("e.auranote:openNote")
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
			latitude : note.get("latitude"),
			longitude : note.get("longitude"),
			id : note.get("id"),
			sort : component.get("v.sort")
		});
		
		action.setCallback(this, function(a){
			var event = $A.get("e.auranote:openNote")
			event.setParams({
				mode : "view",
				note : note
			});
			event.fire();
			
			var event = $A.get("e.auranote:noteAdded");
			event.setParams({noteList : a.getReturnValue()});
			event.fire();
		});
		
		this.runAfter(action);
	},
	
	setLocation : function(component){
		var note = component.getValue("v.note");
		
		var cmpAttrb = component.find("ui_button_set_location").getAttributes();
		var success = function(results){
			note.add("latitude",results.coords.latitude);
			note.add("longitude",results.coords.longitude);
			note.getValue("latitude").setValue(results.coords.latitude);
			note.getValue("longitude").setValue(results.coords.longitude);
			cmpAttrb.setValue("label", note.getValue("longitude").getValue()+", "+note.getValue("latitude").getValue());
			
			//Changing the class value of the button class variable so that the button gets greyed out and it doesn't inherit the
			// the buttons parents css.
			document.getElementsByClassName('locationButton default uiBlock uiButton')[0].setAttribute("class", "locationButton");
			
			//Disabling the button
			cmpAttrb.setValue("disabled", true);
		};
		
		var failure = function(results){
			force.log("failure");
			cmpAttrb.setValue("label", "Failed to get location. Try Again...");
			cmpAttrb.setValue("disabled", false);
		};
		
		if(navigator.geolocation){
			//Async call to try and get the location
			navigator.geolocation.getCurrentPosition(success, failure);
			//Changing the label of the button
			cmpAttrb.setValue("label", "Getting Location");
			
		}else{
			failure();
		}
	}
})