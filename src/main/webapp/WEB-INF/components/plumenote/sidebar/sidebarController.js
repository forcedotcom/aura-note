({
	createNote: function(component) {
		var event = $P.get("e.plumenote:openNote")
		event.setParams({mode : "new", sort : component.get("v.sort")});
		event.fire();
	},
	
	sort : function(component) {
		var sort = component.find("sort").getElement();
		component.getValue("v.sort").setValue(sort.value);
		var action = $P.get("c.plume://ComponentController.getComponent");
		
		action.setParams({
			name : "plumenote:noteList",
		    attributes : {
		    	sort : sort.value
		    }
		});
		
		action.setCallback(this, function(a){
			var event = $P.get("e.plumenote:noteAdded");
			event.setParams({noteList : a.getReturnValue()});
			event.fire();
		});
		
		this.runAfter(action);
	},
	
	noteAdded : function(cmp, event){
		var list = cmp.find("list").getValue("v.body");
		var noteList = $P.componentService.newComponent(event.getParam("noteList"));
		list.destroy();
		list.setValue([noteList]);
	},
	
	search : function(component, event){
		var sort = component.find("sort").getElement();
		var action = $P.get("c.plume://ComponentController.getComponent");
		var query = component.find("searchbox").getElement();
		
		action.setParams({
			name : "plumenote:noteList",
		    attributes : {
		    	sort : sort.value,
		    	query : query.value
		    }
		});
		
		action.setCallback(this, function(a){
			var event = $P.get("e.plumenote:noteAdded");
			event.setParams({noteList : a.getReturnValue()});
			event.fire();
		});
		
		this.runAfter(action);
	}
})