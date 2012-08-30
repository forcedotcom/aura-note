({
	openNote: function(component, event, helper) {
		$L.get("e.plumenote:openNote").setParams({
			note: component.get("v.note")
		}).fire();
	}
})