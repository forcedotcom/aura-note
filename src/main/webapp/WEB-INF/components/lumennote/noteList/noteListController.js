({
	createNote: function(component) {
		var event = $L.get("e.lumennote:openNote")
		event.setParams({mode : "new", sort : component.get("v.sort")});
		event.fire();
	},
	
	sort : function(component) {
		var sort = component.find("sort").getElement();
		component.getValue("v.sort").setValue(sort.value);
	}
})