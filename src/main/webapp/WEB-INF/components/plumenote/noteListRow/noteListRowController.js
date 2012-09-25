({
	openNote: function(component, event, helper) {
		$P.get("e.plumenote:openNote").setParams({
			note: component.get("v.note")
		}).fire();
	}
})