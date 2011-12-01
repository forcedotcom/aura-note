({
	openNote: function(component, event, helper) {
		$L.get("e.lumennote:openNote").setParams({
			note: component.get("v.note")
		}).fire();
	}
})