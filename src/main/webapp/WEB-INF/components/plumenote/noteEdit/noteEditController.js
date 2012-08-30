({
	cancel: function(component) {
		var note = component.getValue("v.note");
		note.getValue("title").rollback();
		note.getValue("body").rollback();
		
		var event = $L.get("e.plumenote:openNote")
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
			var event = $L.get("e.plumenote:openNote")
			event.setParams({
				mode : "view",
				note : note
			});
			event.fire();
			
			var event = $L.get("e.plumenote:noteAdded");
			event.setParams({noteList : a.getReturnValue()});
			event.fire();
		});
		
		this.runAfter(action);
	},
	
	setLocation : function(component){
		var note = component.getValue("v.note");
		
		
		var success = function(results){
			note.add("latitude",results.coords.latitude);
			note.add("longitude",results.coords.longitude);
			note.getValue("latitude").setValue(results.coords.latitude);
			note.getValue("longitude").setValue(results.coords.longitude);
		};
		
		var failure = function(results){
			force.log("failure");
		};
		
		if(navigator.geolocation){
			navigator.geolocation.getCurrentPosition(success, failure);
		}else{
			failure();
		}
	}
})