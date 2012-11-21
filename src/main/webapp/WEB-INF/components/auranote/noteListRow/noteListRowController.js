({
	openNote: function(component, event, helper) {
		$A.get("e.auranote:openNote").setParams({
			note: component.get("v.note")
		}).fire();
	}
})