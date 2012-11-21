({
	render: function(component, helper) {
		setTimeout(function() {
			// Prime the resize pump because we are not making any server requests until someone tries to navigate
			$A.get("e.ui:updateSize").fire();
		}, 400);
		
		return this.superRender();
	}
})