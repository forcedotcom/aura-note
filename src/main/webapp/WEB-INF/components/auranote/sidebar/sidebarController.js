({
	createNote: function(component) {
		var event = $A.get("e.auranote:openNote")
		event.setParams({mode : "new", sort : component.get("v.sort")});
		event.fire();
	},
	
	sort : function(component) {
		var sort = component.find("sort").getElement();
		component.getValue("v.sort").setValue(sort.value);
		var action = $A.get("c.aura://ComponentController.getComponent");
		
		action.setParams({
			name : "auranote:noteList",
		    attributes : {
		    	sort : sort.value
		    }
		});
		
		action.setCallback(this, function(a){
			var event = $A.get("e.auranote:noteAdded");
			event.setParams({noteList : a.getReturnValue()});
			event.fire();
		});
		
		this.runAfter(action);
	},
	
	noteAdded : function(cmp, event){
		var list = cmp.find("list").getValue("v.body");
		var noteList = $A.componentService.newComponent(event.getParam("noteList"));
		list.destroy();
		list.setValue([noteList]);
	},
	
	search : function(component, event){
		var sort = component.find("sort").getElement();
		var action = $A.get("c.aura://ComponentController.getComponent");
		var query = component.find("searchbox").getElement();
		
		action.setParams({
			name : "auranote:noteList",
		    attributes : {
		    	sort : sort.value,
		    	query : query.value
		    }
		});
		
		action.setCallback(this, function(a){
			var event = $A.get("e.auranote:noteAdded");
			event.setParams({noteList : a.getReturnValue()});
			event.fire();
		});
		
		this.runAfter(action);
	}
})